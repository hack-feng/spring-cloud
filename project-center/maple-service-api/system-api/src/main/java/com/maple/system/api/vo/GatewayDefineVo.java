package com.maple.system.api.vo;

import com.fasterxml.jackson.core.type.TypeReference;
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

import java.io.IOException;
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
@ApiModel(value = "GatewayDefine对象", description = "系统配置-gateway动态路由配置")
public class GatewayDefineVo {

    @ApiModelProperty(value = "Eureka注册的服务名,对应routes的- id")
    private String id;

    @ApiModelProperty(value = "对应routes的uri")
    private String uri;

    @ApiModelProperty(value = "路由排序")
    private Integer order;

    @ApiModelProperty(value = "对应routes的filters")
    private List<FilterDefinition> filters = new ArrayList<>();

    @ApiModelProperty(value = "对应routes的predicates")
    private List<PredicateDefinition> predicates = new ArrayList<>();

    public GatewayDefineVo() {
    }

    public GatewayDefineVo(GatewayDefine gatewayRoute) {
        this.id = gatewayRoute.getRouteId();
        this.uri = gatewayRoute.getUri();
        this.order = gatewayRoute.getOrders();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.filters = objectMapper.readValue(gatewayRoute.getFilters(),
                    new TypeReference<List<FilterDefinition>>() {
                    });
            this.predicates = objectMapper.readValue(gatewayRoute.getPredicates(),
                    new TypeReference<List<PredicateDefinition>>() {
                    });
        } catch (IOException e) {
            log.error("网关路由对象转换失败", e);
        }
    }


}
