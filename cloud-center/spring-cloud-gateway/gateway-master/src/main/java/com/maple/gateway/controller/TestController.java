package com.maple.gateway.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RefreshScope
public class TestController {

    // 启动时从配置文件中读取key1为maple.test的value1值
    @Value("${maple.test}")
    private String test;

    @GetMapping("/test")
    public String test(){
        Map<String, Object> map = new HashMap<>();
        map.put("title", "标题");
        map.put("content", "这是内容");
        log.error(JSONObject.toJSONString(map));
        return "test:"+test;
    }
}
