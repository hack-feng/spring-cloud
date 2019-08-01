package com.maple.cloud.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.system.api.bean.GatewayDefine;
import com.maple.system.api.vo.GatewayDefineVo;

/**
 * <p>
 * 系统配置-gateway动态路由配置 服务类
 * </p>
 *
 * @author maple
 * @since 2019-07-30
 */
public interface IGatewayDefineService extends IService<GatewayDefine> {

    boolean add(GatewayDefine gatewayDefine);

    boolean update(GatewayDefine gatewayDefine);

    boolean delete(Integer id);

    GatewayDefineVo get(Integer id);
}
