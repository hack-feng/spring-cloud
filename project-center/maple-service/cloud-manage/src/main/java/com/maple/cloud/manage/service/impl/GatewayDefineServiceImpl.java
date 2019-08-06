package com.maple.cloud.manage.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maple.cloud.manage.dao.GatewayDefineMapper;
import com.maple.cloud.manage.service.IGatewayDefineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.api.bean.GatewayDefine;
import com.maple.system.api.vo.GatewayDefineVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * <p>
 * 系统配置-gateway动态路由配置 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-30
 */
@Slf4j
@Service
public class GatewayDefineServiceImpl extends ServiceImpl<GatewayDefineMapper, GatewayDefine> implements IGatewayDefineService {

    // 路由信息存放在redis的前缀
    private static final String MAPLE_CLOUD_GATEWAY_ROUTES = "maple_cloud_gateway_routes::";
    // 路由信息发布订阅的redis新增修改信道
    public static final String MAPLE_CLOUD_GATEWAY_ROUTES_UPDATE = "maple_cloud_gateway_routes_update";
    // 路由信息发布订阅的redis删除信道
    public static final String MAPLE_CLOUD_GATEWAY_ROUTES_DELETE = "maple_cloud_gateway_routes_delete";

    @Autowired
    private GatewayDefineMapper gatewayDefineMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    @Transactional
    public boolean add(GatewayDefine gatewayDefine){
        //插入到MySql数据库
        Integer id = gatewayDefineMapper.insert(gatewayDefine);
        if(null != id){
            //存放到redis数据库
            stringRedisTemplate.opsForValue().set(MAPLE_CLOUD_GATEWAY_ROUTES + gatewayDefine.getId(),
                    toJson(new GatewayDefineVo(gatewayDefine)));

            stringRedisTemplate.convertAndSend(MAPLE_CLOUD_GATEWAY_ROUTES_UPDATE, toJson(new GatewayDefineVo(gatewayDefine)));
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean update(GatewayDefine gatewayDefine) {
        gatewayDefineMapper.updateById(gatewayDefine);
        updateToRedis(gatewayDefine.getId());
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        GatewayDefine define = gatewayDefineMapper.selectById(id);
        gatewayDefineMapper.deleteById(id);
        stringRedisTemplate.delete(MAPLE_CLOUD_GATEWAY_ROUTES + id);
        stringRedisTemplate.convertAndSend(MAPLE_CLOUD_GATEWAY_ROUTES_DELETE, define.getRouteId());
        return true;
    }

    @Override
    public GatewayDefineVo get(Integer id) {
        String value = stringRedisTemplate.opsForValue().get(MAPLE_CLOUD_GATEWAY_ROUTES + id);
        if(StringUtils.isNotBlank(value)){
            GatewayDefineVo vo = toVo(value, GatewayDefineVo.class);
            if(vo != null){
                return vo;
            }else{
                return updateToRedis(id);
            }
        }else{
            return updateToRedis(id);
        }
    }

    /**
     * 根据id更新redis 并返回 GatewayDefineVo对象
     * @param id GatewayDefine主键id
     * @return GatewayDefineVo
     */
    private GatewayDefineVo updateToRedis(Integer id){
        GatewayDefine gatewayDefine = gatewayDefineMapper.selectById(id);
        if(gatewayDefine != null){
            //存放到redis数据库
            stringRedisTemplate.opsForValue().set(MAPLE_CLOUD_GATEWAY_ROUTES + gatewayDefine.getId(),
                    toJson(new GatewayDefineVo(gatewayDefine)));
            stringRedisTemplate.convertAndSend(MAPLE_CLOUD_GATEWAY_ROUTES_UPDATE, toJson(new GatewayDefineVo(gatewayDefine)));
        }
        return new GatewayDefineVo(gatewayDefine);
    }

    /**
     * vo转换为json
     *
     * @param obj redis需要的vo
     * @return json string
     */
    private String toJson(Object obj) {
        String routeDefinitionJson = Strings.EMPTY;
        try {
            routeDefinitionJson = new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象序列化为json String", e);
        }
        return routeDefinitionJson;
    }

    /**
     * json转换为Vo
     * @param json 从redis取到的vo对象的json
     * @param clazz 返回对象类型
     * @return T
     */
    private <T> T toVo(String json, Class<T> clazz){
        T vo = null;
        try {
            vo = new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error("json转换为vo对象", e);
        }
        return vo;
    }
}
