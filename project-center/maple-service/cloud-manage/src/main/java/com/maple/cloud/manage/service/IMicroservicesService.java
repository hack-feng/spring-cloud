package com.maple.cloud.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.system.api.bean.Microservices;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
public interface IMicroservicesService extends IService<Microservices> {

    IPage<Microservices> getList();
}
