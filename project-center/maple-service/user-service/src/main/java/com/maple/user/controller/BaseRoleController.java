package com.maple.user.controller;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.util.R;
import com.maple.user.service.IBaseRoleResService;
import com.maple.user.service.IBaseRoleService;
import com.maple.userapi.bean.BaseRole;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;

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
    @Autowired
    private IBaseRoleResService roleResService;

    /**
     * 分页查询用户
     *
     * @param page 参数集
     * @param role 查询参数列表
     * @return 用户集合
     * @author zhua
     */
    @GetMapping("/page")
    public R getUserPage(Page page, BaseRole role) {
        return R.ok(roleService.getRolePage(page, role));
    }

    /**
     * 添加角色
     *
     * @param role
     * @return
     * @author zhua
     */
    @PostMapping("/add")
    public R add(BaseRole role) {
        if (role == null) {
            return R.failed("获取角色信息失败");
        }
        role.setIsDelete(Convert.toInt(CommonConstants.STATUS_NORMAL));
        role.setStatus(Convert.toInt(CommonConstants.STATUS_NORMAL));
        role.setCreateDate(new Date());
        return R.ok(roleService.save(role));
    }

    /**
     * 编辑角色
     *
     * @param role
     * @return
     * @author zhua
     */
    @PostMapping("/update")
    public R update(BaseRole role) {
        if (role == null) {
            return R.failed("获取角色信息失败");
        }
        role.setModifyDate(new Date());
        return R.ok(roleService.updateById(role));
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     * @author zhua
     */
    @DeleteMapping("/delete/{ids}")
    public R delete(@PathVariable("ids") String ids) {
        if (ids == null) {
            return R.failed("获取角色信息失败");
        }
        return R.ok(roleService.deleteByIds(ids));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     * @author zhua
     */
    @GetMapping("/list")
    public R listRoles() {
        return R.ok(roleService.list());
    }

    /**
     * 授权
     *
     * @param roleId
     * @param resIds
     * @return
     * @author zhua
     */
    @PostMapping("/updateRoleAuth")
    public R updateRoleAuth(Integer roleId, String resIds) {
        return R.ok(roleResService.updateRoleAuth(roleId, resIds));
    }
}

