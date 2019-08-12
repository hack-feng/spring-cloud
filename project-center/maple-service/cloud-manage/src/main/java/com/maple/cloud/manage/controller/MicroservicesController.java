package com.maple.cloud.manage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.Microservices;
import com.maple.system.api.ro.MicroservicesRo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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

    @PostMapping
    public R add(@RequestBody MicroservicesRo microservicesRo){
        Microservices microservices = microservicesRo.toBean(Microservices.class);
        return microservicesService.add(microservices);
    }
}