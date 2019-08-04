package com.maple.user.service.impl;

import com.maple.user.dao.BaseResourcesMapper;
import com.maple.user.service.IBaseResourcesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 基础信息-资源表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseResourcesServiceImpl extends ServiceImpl<BaseResourcesMapper, BaseResources> implements IBaseResourcesService {

    @Autowired
    private BaseResourcesMapper baseResourcesMapper;

    @Override
    public List<BaseResources> getMenuByRoleCode(String roleCode) {
        return baseResourcesMapper.getResourcesByRoleCode(roleCode);
    }
}
