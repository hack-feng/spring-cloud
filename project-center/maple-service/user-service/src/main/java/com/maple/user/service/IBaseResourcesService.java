package com.maple.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.userapi.bean.BaseResources;

import java.util.List;

/**
 * <p>
 * 基础信息-资源表 服务类
 * </p>
 *
 * @author maple
 * @since 2019-07-09
 */
public interface IBaseResourcesService extends IService<BaseResources> {

    /**
     * 根据角色代码查询
     *
     * @param roleCode
     * @return
     */
    List<BaseResources> getMenuByRoleCode(String roleCode);
}
