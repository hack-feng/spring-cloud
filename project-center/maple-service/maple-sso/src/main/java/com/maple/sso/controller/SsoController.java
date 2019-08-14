package com.maple.sso.controller;

import com.alibaba.fastjson.JSONObject;
import com.maple.userapi.bean.BaseUser;
import com.maple.userapi.fegin.UserFeginInterface;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("sso")
class SsoController {

    @Autowired
    private RestTemplate restTemplate;

//    @Autowired
//    private UserFeginInterface userFeginInterface;

    @HystrixCommand(fallbackMethod = "loginHys")
    @RequestMapping(value = "login")
    public JSONObject sso() {
        JSONObject result = restTemplate.getForEntity("http://127.0.0.1:5001/user/baseUser/getList", JSONObject.class).getBody();
        System.out.println(result);
        return result;
    }

    private JSONObject loginHys() {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "进入熔断器");
        return a;
    }

    @HystrixCommand(fallbackMethod = "getUserInfoHys")
    @RequestMapping(value = "getUserInfo")
    public JSONObject getUserInfo() {
        JSONObject result = new JSONObject();
        JSONObject a = restTemplate.getForEntity("http://127.0.0.1:5001/user/baseUser/getUserInfo?id=1", JSONObject.class).getBody();
        result.put("code", 200);
        result.put("data", a);
        return result;
    }

    private JSONObject getUserInfoHys() {
        JSONObject a = new JSONObject();
        a.put("code", 500);
        a.put("data", "进入getUserInfoHys熔断器");
        return a;
    }
}
