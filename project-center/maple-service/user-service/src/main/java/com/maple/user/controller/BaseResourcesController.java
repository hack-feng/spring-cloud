package com.maple.user.controller;


import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.util.R;
import com.maple.common.security.util.SecurityUtils;
import com.maple.user.service.IBaseResourcesService;
import com.maple.userapi.bean.BaseResources;
import com.maple.userapi.util.TreeUtil;
import com.maple.userapi.vo.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 基础信息-资源表 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/baseResources")
public class BaseResourcesController {

    @Autowired
    private IBaseResourcesService baseResourcesService;

    /**
     * 返回当前用户的树形菜单集合
     * @author zhua
     * @return 当前用户的树形菜单
     */
    @GetMapping("getUserMenu")
    public R getUserMenu() {
        // 获取符合条件的菜单
        Set<BaseResources> all = new HashSet<>();
        SecurityUtils.getRoles()
                .forEach(roleCode -> all.addAll(baseResourcesService.getMenuByRoleCode(roleCode)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> CommonConstants.MENU.equals(Convert.toStr(menuVo.getResType())))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        return R.ok(TreeUtil.buildByRecursive(menuTreeList, -1));
    }

    /**
     * 获取菜单树
     * @author zhua
     * @return
     */
    @GetMapping("getMenuTree")
    public R getMenuTree() {
        QueryWrapper<BaseResources> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",CommonConstants.STATUS_NORMAL);
        List<BaseResources> menuList = baseResourcesService.list(queryWrapper);
        if(menuList == null || menuList.size() == 0) {
            return R.ok(null,"无菜单树");
        }
        List<MenuTree> menuTreeList = menuList.stream()
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());;
        return R.ok(TreeUtil.buildByRecursive(menuTreeList, -1));
    }

    /**
     * 添加
     * @return
     */
    @PostMapping("addMenu")
    public R addMenu(BaseResources res) {
        if(res == null) {
            return R.failed("获取资源信息失败");
        }
        res.setCreateDate(new Date());
        res.setModifyDate(new Date());
        return R.ok(baseResourcesService.save(res));
    }

    /**
     * 修改
     * @return
     */
    @PostMapping("updateMenu")
    public R updateMenu(BaseResources res) {
        if(res == null) {
            return R.failed("获取资源信息失败");
        }
        res.setModifyDate(new Date());
        return R.ok(baseResourcesService.updateById(res));
    }

}

