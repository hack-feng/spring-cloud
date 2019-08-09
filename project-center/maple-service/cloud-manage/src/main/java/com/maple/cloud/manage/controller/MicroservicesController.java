package com.maple.cloud.manage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.Microservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@RestController
@RequestMapping("/microservices")
public class MicroservicesController {

    @Autowired
    private IMicroservicesService microservicesService;

    @GetMapping("/getList")
    public R getList(){
        IPage<Microservices> result = microservicesService.getList();
        return R.ok(result);
    }
}