package com.maple.cloud.manage.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.Microservices;
import com.maple.system.api.ro.MicroservicesRo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Api(value = "微服务配置模块")
@RestController
@RequestMapping("/microservices")
public class MicroservicesController {

    @Autowired
    private IMicroservicesService microservicesService;

    @GetMapping("/getList")
    public R getList(Page page) {
        IPage<Microservices> result = microservicesService.getList();
        return R.ok(result);
    }

    @ApiOperation(value = "新增微服务", notes = "新增一个微服务，根据选择判断是否自动生成config配置文件")
    @PostMapping
    public R add(@Valid MicroservicesRo microservicesRo) {
        Microservices microservices = microservicesRo.toBean(Microservices.class);
        microservices.setCreateDate(new Date());
        return microservicesService.add(microservices);
    }

    @ApiOperation(value = "修改微服务", notes = "根据id修改一个微服务，不会修改config信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, paramType = "path")
    @PutMapping(value = "/{id}")
    public R update(@PathVariable Integer id, @Valid MicroservicesRo microservicesRo) {
        if (id == null) {
            return R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        Microservices microservices = microservicesRo.toBean(Microservices.class);
        microservices.setServiceName(null);
        microservices.setServicePort(null);
        microservices.setModifyDate(new Date());
        microservices.setId(id);
        return microservicesService.update(microservices);
    }

    @ApiOperation(value = "删除微服务", notes = "根据id删除一个微服务，同时删除该服务对应的所有信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, dataType = "int", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public R delete(@PathVariable Integer id) {
        if (id == null) {
            return R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        return microservicesService.delete(id);
    }
}