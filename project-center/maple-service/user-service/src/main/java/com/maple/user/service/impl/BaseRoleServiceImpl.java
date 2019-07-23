package com.maple.user.service.impl;

import com.maple.user.dao.BaseRoleMapper;
import com.maple.user.service.IBaseRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseRole;
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

}
