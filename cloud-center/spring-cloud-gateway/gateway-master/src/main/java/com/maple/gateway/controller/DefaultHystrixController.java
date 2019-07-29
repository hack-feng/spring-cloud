package com.maple.gateway.controller;

import com.maple.common.core.util.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认降级处理
 * @author Maple
 */
@RestController
public class DefaultHystrixController {
    @RequestMapping("/defaultfallback")
    public R defaultfallback(){
        System.out.println("降级操作...");
        return R.failed("系统繁忙，请稍候再试...");
    }
}
