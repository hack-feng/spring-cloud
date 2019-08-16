package com.maple.cloud.manage.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.maple.cloud.manage.service.IConfigPropertiesService;
import com.maple.cloud.manage.service.IMicroservicesService;
import com.maple.common.core.constant.CommonConstants;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.ConfigProperties;
import com.maple.system.api.ro.ConfigPropertiesRo;
import com.maple.system.api.ro.MicroservicesRo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
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
@Slf4j
@RestController
@RequestMapping("/configProperties")
public class ConfigPropertiesController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private IConfigPropertiesService configPropertiesService;
    @Autowired
    private IMicroservicesService microservicesService;

    @ApiOperation(value = "查询微服务列表", notes = "分页查询微服务的列表")
    @GetMapping("/getList")
    public R getList(ConfigPropertiesRo configPropertiesRo) {
        if (StringUtils.isEmpty(configPropertiesRo.getApplication())) {
            return R.failed("服务名称不能为空");
        }
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        List<ConfigProperties> list = configPropertiesService.getList(configProperties);
        return R.ok(list);
    }

    @ApiOperation(value = "新增配置信息", notes = "新增一个配置信息")
    @PostMapping
    public R add(@Valid ConfigPropertiesRo configPropertiesRo) {
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        configProperties.setCreateDate(new Date());
        R result=  configPropertiesService.add(configProperties);
        // 刷新单个项目的config配置
        refreshResult(result.getCode(), configProperties.getApplication());
        return result;
    }

    @ApiOperation(value = "修改配置信息", notes = "根据id修改一个配置信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, paramType = "path")
    @PutMapping(value = "/{id}")
    public R update(@PathVariable Integer id, @Valid ConfigPropertiesRo configPropertiesRo) {
        if (id == null) {
            return R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        ConfigProperties configProperties = configPropertiesRo.toBean(ConfigProperties.class);
        configProperties.setModifyDate(new Date());
        configProperties.setId(id);
        R result = configPropertiesService.update(configProperties);
        // 刷新单个项目的config配置
        refreshResult(result.getCode(), configProperties.getApplication());
        return result;
    }

    @ApiOperation(value = "删除配置信息", notes = "根据id删除一个配置信息")
    @ApiImplicitParam(name = "id", value = "微服务id，路由地址", required = true, dataType = "int", paramType = "path")
    @DeleteMapping(value = "/{id}")
    public R delete(@PathVariable Integer id) {
        if (id == null) {
            return R.failed("错误代码：ID IS NULL, 请刷新页面重试");
        }
        return configPropertiesService.delete(id);
    }

    /**
     * bus-refresh，刷新单个项目的config配置
     * @param code
     * @param application
     */
    public void refreshResult(int code, String application){
        if(code == CommonConstants.SUCCESS){
            Map map = new HashMap();
            MicroservicesRo configMic = microservicesService.getByServiceName("config-master");
            MicroservicesRo appMic = microservicesService.getByServiceName(application);
            String url = "http://"+configMic.getServiceIp()+":"+configMic.getServicePort()+"/actuator/bus-refresh/"
                    + application + ":" + appMic.getServicePort();
            String refreshResult = restTemplate.postForEntity(url, map, String.class).getBody();
            log.info(application+"刷新配置文件，刷新地址："+ url + "执行结果：" + refreshResult);
        }
    }
}

