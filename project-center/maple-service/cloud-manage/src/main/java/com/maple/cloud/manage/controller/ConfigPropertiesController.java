package com.maple.cloud.manage.controller;


import com.maple.cloud.manage.service.IConfigPropertiesService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.ConfigProperties;
import com.maple.system.api.ro.ConfigPropertiesRo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统配置-config动态配置 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-08-08
 */
@Api(value = "系统配置-config动态配置")
@RestController
@RequestMapping("/configProperties")
public class ConfigPropertiesController {
    @Autowired
    private IConfigPropertiesService configPropertiesService;

    @GetMapping("/getList")
    public R getList(ConfigPropertiesRo configPropertiesRo){
        if(configPropertiesRo.getApplication() == null){
            return R.failed("服务名称不能为空");
        }
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        List<ConfigProperties> list = configPropertiesService.getList(configProperties);
        return R.ok(list);
    }

    @ApiOperation(value = "新增配置信息", notes = "新增一个配置信息")
    @PostMapping
    public R add(ConfigPropertiesRo configPropertiesRo){
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        configProperties.setCreateDate(new Date());
        return configPropertiesService.add(configProperties);
    }

    @ApiOperation(value = "修改配置信息", notes = "根据id修改一个配置信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, paramType = "path")
    @PutMapping(value = "/{id}")
    public R update(@PathVariable Integer id, ConfigPropertiesRo configPropertiesRo){
        if(id == null){
            return  R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        configProperties.setModifyDate(new Date());
        configProperties.setId(id);
        return configPropertiesService.update(configProperties);
    }

    @ApiOperation(value = "删除配置信息", notes = "根据id删除一个配置信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, dataType = "int", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public R delete(@PathVariable Integer id){
        if(id == null){
            return  R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        return configPropertiesService.delete(id);
    }
}

