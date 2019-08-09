package com.maple.cloud.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.cloud.manage.mapper.MicroservicesMapper;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.api.bean.Microservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Service
public class MicroservicesServiceImpl extends ServiceImpl<MicroservicesMapper, Microservices> implements IMicroservicesService {

    @Autowired
    private MicroservicesMapper microservicesMapper;

    @Override
    public IPage<Microservices> getList() {
        Page<Microservices> page = new Page<>();
        IPage<Microservices> list = microservicesMapper.selectPage(page, new QueryWrapper<>());
        return list;
    }
}
