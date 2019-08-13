package com.maple.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.user.dao.BaseRoleMapper;
import com.maple.user.dao.BaseRoleResMapper;
import com.maple.user.service.IBaseRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础信息-用户角色表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseRoleServiceImpl extends ServiceImpl<BaseRoleMapper, BaseRole> implements IBaseRoleService {

    @Autowired
    private BaseRoleMapper baseRoleMapper;
    @Autowired
    private BaseRoleResMapper baseRoleResMapper;

    @Override
    public IPage getRolePage(Page page, BaseRole role) {
        return baseRoleMapper.getRolePage(page,role);
    }

    @Override
    public String deleteByIds(String ids) {
        String[] idArr = ids.split(",");
        //删除角色菜单关联关系
        baseRoleResMapper.deleteByRoleId(idArr);
        baseRoleMapper.deleteByIds(idArr);
        return "删除成功!";
    }

}
