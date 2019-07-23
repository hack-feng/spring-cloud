package com.maple.user.service.impl;

import com.maple.user.dao.BaseRoleResMapper;
import com.maple.user.service.IBaseRoleResService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.userapi.bean.BaseRoleRes;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 基础信息-角色和资源关联表 服务实现类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
@Service
public class BaseRoleResServiceImpl extends ServiceImpl<BaseRoleResMapper, BaseRoleRes> implements IBaseRoleResService {

}
