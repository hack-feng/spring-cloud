package com.maple.user.service.impl;

import com.maple.user.dao.BaseUserRoleMapper;
import com.maple.user.service.IBaseUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseUserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础信息-用户和角色关联表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseUserRoleServiceImpl extends ServiceImpl<BaseUserRoleMapper, BaseUserRole> implements IBaseUserRoleService {

}
