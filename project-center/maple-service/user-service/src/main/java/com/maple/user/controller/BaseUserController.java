package com.maple.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.maple.user.service.IBaseUserService;
import com.maple.userapi.bean.BaseUser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
public class BaseUserController {

    @Autowired
    private IBaseUserService userService;

    @HystrixCommand(fallbackMethod = "baseHys")
    @GetMapping(value = "getList")
    public JSONObject getList(){
        JSONObject result = new JSONObject();
        List<BaseUser> list = userService.list(null);
        result.put("code", 200);
        result.put("data", list);
        return result;
    }

    private JSONObject baseHys(){
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }

    @HystrixCommand(fallbackMethod = "getUserInfoHys")
    @RequestMapping(value = "getUserInfo")
    public JSONObject getUserInfo(Integer id){
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

    private JSONObject getUserInfoHys(Integer id){
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "BaseUserController的线路中断");
        return a;
    }

}

