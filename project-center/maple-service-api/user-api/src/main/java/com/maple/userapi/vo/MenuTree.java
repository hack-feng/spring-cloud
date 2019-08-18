package com.maple.userapi.vo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.maple.userapi.bean.BaseResources;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private String resCode;

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
     * 菜单类型 （1菜单 2按钮）
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
        this.resCode = resource.getResCode();
        this.path = resource.getPath();

        this.component = resource.getComponent();
        this.type = Convert.toStr(resource.getResType());
        this.sort = resource.getSortNum();
        this.title = resource.getResName();

        this.meta = new Meta();
        this.meta.setTitle(resource.getResName());
        if (StrUtil.isNotEmpty(resource.getComponent()) && resource.getComponent().startsWith("http")) {
            this.meta.setHref(resource.getComponent());
        }
        this.meta.setIcon(resource.getIcon());
    }


}
