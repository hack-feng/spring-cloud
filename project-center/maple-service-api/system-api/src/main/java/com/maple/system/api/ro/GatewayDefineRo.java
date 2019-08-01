package com.maple.system.api.ro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maple.common.core.bean.BaseRo;
import com.maple.system.api.bean.FilterDefinition;
import com.maple.system.api.bean.GatewayDefine;
import com.maple.system.api.bean.PredicateDefinition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统配置-gateway动态路由配置
 * </p>
 *
 * @author maple
 * @since 2019-07-31
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="GatewayDefine对象", description="系统配置-gateway动态路由配置")
public class GatewayDefineRo extends BaseRo<GatewayDefine> {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "对应routes的uri")
    @NotBlank(message = "uri不能为空")
    private String uri;

    @ApiModelProperty(value = "对应routes的predicates")
    @NotEmpty(message = "predicates不能为空")
    private List<PredicateDefinition> predicates = new ArrayList<>();

    @ApiModelProperty(value = "对应routes的filters")
    private List<FilterDefinition> filters = new ArrayList<>();

    @ApiModelProperty(value = "Eureka注册的服务名,对应routes的- id")
    @NotBlank(message = "routeId不能为空")
    private String routeId;

    @ApiModelProperty(value = "路由描述")
    private String description;

    @ApiModelProperty(value = "路由排序")
    private Integer orders;

    @ApiModelProperty(value = "状态（0：启用 ，1：停用）")
    private Integer status;

    @Override
    public GatewayDefine toBean(Class<GatewayDefine> clazz) {
        GatewayDefine gatewayRoute = new GatewayDefine();
        BeanUtils.copyProperties(this, gatewayRoute);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            gatewayRoute.setFilters(objectMapper.writeValueAsString(this.getFilters()));
            gatewayRoute.setPredicates(objectMapper.writeValueAsString(this.getPredicates()));
        } catch (JsonProcessingException e) {
            log.error("网关filter或predicates配置转换异常", e);
        }
        return gatewayRoute;
    }

}
