package com.maple.user.controller;


import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.util.R;
import com.maple.common.security.util.SecurityUtils;
import com.maple.user.service.IBaseResourcesService;
import com.maple.userapi.bean.BaseResources;
import com.maple.userapi.util.TreeUtil;
import com.maple.userapi.vo.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
     *
     * @return 当前用户的树形菜单
     */
    @GetMapping("getUserMenu")
    public R getUserMenu() {
        // 获取符合条件的菜单
        Set<BaseResources> all = new HashSet<>();
        SecurityUtils.getRoles()
                .forEach(roleCode -> all.addAll(baseResourcesService.getMenuByRoleCode(roleCode)));
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuVo -> CommonConstants.MENU.equals(menuVo.getResType()))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        return R.ok(TreeUtil.buildByRecursive(menuTreeList, -1));
    }

}

