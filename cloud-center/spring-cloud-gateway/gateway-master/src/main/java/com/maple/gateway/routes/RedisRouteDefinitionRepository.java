package com.maple.gateway.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.*;

/**
 * 动态的从redis读取路由信息
 */
@Slf4j
@Component
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    //路由信息存放在redis的前缀
    private static final String MAPLE_CLOUD_GATEWAY_ROUTES = "maple_cloud_gateway_routes::";
    private static volatile Map<String, RouteDefinition> routeDefinitionMaps = new HashMap<>();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        if (routeDefinitionMaps == null || routeDefinitionMaps.size() == 0) {
            loadRouteDefinition();
        }
        return Flux.fromIterable(routeDefinitionMaps.values());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(routeDefinition -> {
            routeDefinitionMaps.put(routeDefinition.getId(), routeDefinition);
            return Mono.empty();
        });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            routeDefinitionMaps.remove(id);
            return Mono.empty();
        });
    }

    /**
     * redis topic "saveRouteDefinition" 消费者
     * 动态新增或修改路由
     *
     * @param routeDefinitionJson
     */
    public void saveRouteDefinition(String routeDefinitionJson) {
        log.info("saveRouteDefinition--------------->" + routeDefinitionJson);
        try {
            RouteDefinition routeDefinition = new ObjectMapper().readValue(routeDefinitionJson, RouteDefinition.class);
            routeDefinitionMaps.put(routeDefinition.getId(), routeDefinition);
        } catch (IOException e) {
            log.info("动态修改路由数据转换失败--------------" + e.getMessage());
        }
    }

    /**
     * redis topic "deleteRouteDefinition" 消费者
     * 动态删除路由
     *
     * @param routeId
     */
    public void deleteRouteDefinition(String routeId) {
        log.info("deleteRouteDefinition----------->" + routeId);
        routeDefinitionMaps.remove(routeId);
    }

    /**
     * 初始化路由中redis的数据
     */
    public void loadRouteDefinition() {
        Set<String> gatewayKeys = stringRedisTemplate.keys(MAPLE_CLOUD_GATEWAY_ROUTES + "*");
        if (CollectionUtils.isEmpty(gatewayKeys)) {
            return;
        }
        List<String> gatewayRoutes = stringRedisTemplate.opsForValue().multiGet(gatewayKeys);
        gatewayRoutes.forEach(value -> {
            try {
                RouteDefinition routeDefinition = new ObjectMapper().readValue(value, RouteDefinition.class);
                routeDefinitionMaps.put(routeDefinition.getId(), routeDefinition);
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        });
    }
}
