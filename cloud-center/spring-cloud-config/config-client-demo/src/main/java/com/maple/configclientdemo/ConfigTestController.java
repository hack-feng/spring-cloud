package com.maple.configclientdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigTestController {


    @Value("${mycontent}")
    private String mycontent;

    @GetMapping(value = "test")
    public String test(){
        return "获取到配置中心的内容:" + mycontent;
    }
}
