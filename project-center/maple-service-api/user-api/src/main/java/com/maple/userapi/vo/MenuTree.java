package com.maple.userapi.vo;

import cn.hutool.core.convert.Convert;
import com.maple.userapi.bean.BaseResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = true)
public class MenuTree extends TreeNode {

    /**
     * 权限名称
     */
    private String name;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单权限标识
     */
    private String resource;

    /**
     * 前端URL
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * VUE页面
     */
    private String component;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 0-开启，1- 关闭
     */
    private String keepAlive;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除标记(0--正常 1--删除)
     */
    private String delFlag;
    /**
     * 是否隐藏(0--显示  1--隐藏)
     */
    private boolean hideInMenu;
    /**
     * 是否总显示
     */
    private boolean showAlways;
    /**
     * 是否缓存(0--否 1--是)
     */
    private boolean notCache;
    /**
     * 菜单权限数组
     */
    private String access;
    /**
     * 用于跳转到外部连接
     */
    private String href;

    private Meta meta;

    public MenuTree() {
    }

    public MenuTree(int id, String name, int parentId) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.title = name;
    }

    public MenuTree(int id, String name, MenuTree parent) {
        this.id = id;
        this.parentId = parent.getId();
        this.name = name;
        this.title = name;
    }

    public MenuTree(BaseResources resource) {
        this.id = resource.getId();
        this.parentId = resource.getParentId();
        this.icon = resource.getIcon();
        this.name = resource.getResName();
        this.resource = resource.getResCode();
        this.path = resource.getResUrl();
        this.component = resource.getComponent();
        this.type = Convert.toStr(resource.getResType());
        this.sort = resource.getSortNum();
//        this.keepAlive = resource.getKeepAlive();
        this.title = resource.getResName();
//        this.showAlways = resource.isShowAlways();
        this.href = resource.getResUrl();
//        this.hideInMenu = resource.isHideInMenu();
//        this.notCache = resource.isNotCache();
        this.delFlag = Convert.toStr(resource.getIsDelete());
//        this.access = resource.getAccess();

        this.meta = new Meta();
        this.meta.setTitle(resource.getResName());
//        this.meta.setShowAlways(resource.isShowAlways());
//        this.meta.setHideInMenu(resource.isHideInMenu());
        this.meta.setHref(resource.getResUrl());
        this.meta.setIcon(resource.getIcon());
//        this.meta.setNotCache(resource.isNotCache());
//        if(StrUtil.isNotEmpty(resource.getAccess())) {
//            String access = resource.getAccess();
            this.meta.setAccess(Arrays.asList(access.split(",")));
    }
}
