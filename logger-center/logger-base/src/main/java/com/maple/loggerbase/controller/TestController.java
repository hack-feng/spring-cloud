package com.maple.loggerbase.controller;

import com.maple.common.core.config.GlobalConfig;
import com.maple.common.core.util.LogHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "日志测试")
@RestController
public class TestController {


    @LogHelper(logType = GlobalConfig.logTypeEnum.INTERFACE)
    @ApiOperation(value = "日志测试controller", notes = "日志测试controller")
    @ApiImplicitParam(name = "id", value = "日志测试id", required = true, dataType = "int")
    @RequestMapping(value = "/test")
    public String test(String id) {
        System.out.println(id);
        return "这是一个日志测试的controller";
    }
}
