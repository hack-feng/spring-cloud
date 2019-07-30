package com.maple.cloud.manage.service.impl;

import com.maple.cloud.manage.dao.GatewayDefineMapper;
import com.maple.cloud.manage.service.IGatewayDefineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.api.bean.GatewayDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置-gateway动态路由配置 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-30
 */
@Service
public class GatewayDefineServiceImpl extends ServiceImpl<GatewayDefineMapper, GatewayDefine> implements IGatewayDefineService {


    private static final String MAPLE_CLOUD_GATEWAY_ROUTES = "maple_cloud_gateway_routes::";
    @Autowired
    private GatewayDefineMapper gatewayDefineMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean save(GatewayDefine gatewayDefine){
        Integer id = gatewayDefineMapper.insert(gatewayDefine);

        if(id != null){

        }
        return false;

    }
}
