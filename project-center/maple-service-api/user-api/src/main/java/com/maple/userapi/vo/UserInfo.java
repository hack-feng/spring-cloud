package com.maple.userapi.vo;

import com.maple.userapi.bean.BaseUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @date 2019/8/4
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private BaseUser sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 角色集合
     */
    private String[] roles;
}
