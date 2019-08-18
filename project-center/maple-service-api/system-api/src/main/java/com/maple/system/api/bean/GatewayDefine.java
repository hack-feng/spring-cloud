package com.maple.system.api.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统配置-gateway动态路由配置
 * </p>
 *
 * @author maple
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("system_gateway_define")
@ApiModel(value = "GatewayDefine对象", description = "系统配置-gateway动态路由配置")
public class GatewayDefine extends Model<GatewayDefine> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "对应routes的uri")
    private String uri;

    @ApiModelProperty(value = "对应routes的predicates")
    private String predicates;

    @ApiModelProperty(value = "对应routes的filters")
    private String filters;

    @ApiModelProperty(value = "Eureka注册的服务名,对应routes的- id")
    @TableField("route_id")
    private String routeId;

    @ApiModelProperty(value = "路由描述")
    private String description;

    @ApiModelProperty(value = "路由排序")
    private Integer orders;

    @ApiModelProperty(value = "状态（0：启用 ，1：停用）")
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
