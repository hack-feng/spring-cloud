package com.maple.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.userapi.bean.BaseRole;

/**
 * <p>
 * 基础信息-用户角色表 服务类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
public interface IBaseRoleService extends IService<BaseRole> {

    IPage getRolePage(Page page, BaseRole role);

    String deleteByIds(String ids);
}
