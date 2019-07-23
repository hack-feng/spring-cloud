package com.maple.loggerbase.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${myww}")
    private String myww;

    @RequestMapping(value = "/test")
    public String test(){
        System.out.println(myww);
        return myww;
    }
}
