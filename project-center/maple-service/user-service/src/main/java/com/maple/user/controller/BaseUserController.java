package com.maple.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.core.util.R;
import com.maple.common.security.util.SecurityUtils;
import com.maple.user.service.IBaseUserService;
import com.maple.userapi.bean.BaseUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 基础信息-用户信息 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-07-13
 */
@RestController
@RequestMapping("/baseUser")
@RefreshScope
public class BaseUserController {

    @Autowired
    private IBaseUserService userService;

    /**
     * 获取当前用户全部信息
     *
     * @return zhua
     */
    @GetMapping(value = {"/info"})
    public R info() {
        String username = SecurityUtils.getUsername();
        BaseUser user = userService.getOne(Wrappers.<BaseUser>query()
                .lambda().eq(BaseUser::getUserName, username));
        if (user == null) {
            return R.failed("获取当前用户信息失败");
        }
        return R.ok(userService.getUserInfo(user));
    }

    @HystrixCommand(fallbackMethod = "baseHys")
    @GetMapping(value = "getList")
    public JSONObject getList() {
        JSONObject result = new JSONObject();
        List<BaseUser> list = userService.list(null);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }

    private JSONObject baseHys() {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }

    @HystrixCommand(fallbackMethod = "getUserInfoHys")
    @RequestMapping(value = "getUserInfo")
    public JSONObject getUserInfo(Integer id) {
        JSONObject result = new JSONObject();
        BaseUser user = null;
        try {
            user = userService.getById(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("code", 200);
        result.put("data", user);
        return result;
    }

    private JSONObject getUserInfoHys(Integer id) {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }


    /**
     * 分页查询用户
     *
     * @param page 参数集
     * @param user 查询参数列表
     * @return 用户集合
     */
    @GetMapping("/page")
    public R getUserPage(Page page, BaseUser user) {
        return R.ok(userService.getUserPage(page, user));
    }

}

