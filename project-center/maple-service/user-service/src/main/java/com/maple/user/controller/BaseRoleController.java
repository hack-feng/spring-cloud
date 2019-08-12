package com.maple.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.core.util.R;
import com.maple.user.service.IBaseRoleService;
import com.maple.userapi.bean.BaseRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 基础信息-用户角色表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/baseRole")
public class BaseRoleController {

    @Autowired
    private IBaseRoleService roleService;

    /**
     * 分页查询用户
     *
     * @param page    参数集
     * @param role 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, BaseRole role) {
        return R.ok(roleService.getRolePage(page, role));
    }


}

