package com.maple.cloud.manage.controller;


import com.maple.cloud.manage.service.IGatewayDefineService;
import com.maple.common.core.util.R;
import com.maple.system.api.bean.GatewayDefine;
import com.maple.system.api.ro.GatewayDefineRo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统配置-gateway动态路由配置 前端控制器
 * </p>
 *
 * @author maple
 * @since 2019-07-30
 */
@RestController
@RequestMapping("/gateway/define")
public class GatewayDefineController {

    @Autowired
    private IGatewayDefineService gatewayDefineService;

    @ApiOperation(value = "新增网关路由", notes = "新增一个网关路由")
    @ApiImplicitParam(name = "gatewayDefineRo", value = "需要新增网关实体对象", required = true,
            dataType = "GatewayDefineRo", dataTypeClass = GatewayDefineRo.class)
    @PostMapping
    public R add(@RequestBody GatewayDefineRo gatewayDefineRo){
       boolean isOk = gatewayDefineService.add(gatewayDefineRo.toBean(GatewayDefine.class));
       return R.isOk(isOk, "新增网关路由");
    }

    @ApiOperation(value = "修改网关路由", notes = "根据id修改指定的网关路由")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "网关路由id", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "gatewayDefineRo", value = "需要修改的网关实体对象", required = true,
                    dataType = "GatewayDefineRo", dataTypeClass = GatewayDefineRo.class)
    })
    @PutMapping(value = "/{id}")
    public R update(@PathVariable Integer id, GatewayDefineRo gatewayDefineRo){
        GatewayDefine gatewayDefine = gatewayDefineRo.toBean(GatewayDefine.class);
        gatewayDefine.setId(id);
        return R.isOk(gatewayDefineService.update(gatewayDefine), "修改网关路由");
    }


    @ApiOperation(value = "删除网关路由", notes = "根据id删除指定的网关路由")
    @ApiImplicitParam(name = "id", value = "网关路由id", required = true, dataType = "Integer")
    @DeleteMapping(value = "/{id}")
    public R delete(@PathVariable Integer id){
        return R.isOk(gatewayDefineService.delete(id), "删除网关路由");
    }

    @GetMapping(value = "/{id}")
    public R get(@PathVariable Integer id){
        return R.ok(gatewayDefineService.get(id));
    }

}

