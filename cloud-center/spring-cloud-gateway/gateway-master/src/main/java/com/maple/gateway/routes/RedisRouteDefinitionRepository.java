package com.maple.gateway.routes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
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
    private Map<String, RouteDefinition> routeDefinitionMaps = new HashMap<>();

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        loadRouteDefinition();
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
            if (this.routeDefinitionMaps.containsKey(id)) {
                this.routeDefinitionMaps.remove(id);
                return Mono.empty();
            } else {
                return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
            }
        });
    }

    private void loadRouteDefinition() {
        //删除时没有触发delete，导致这里需要清空routeDefinitionMaps
        routeDefinitionMaps.clear();
        Set<String> gatewayKeys = stringRedisTemplate.keys(MAPLE_CLOUD_GATEWAY_ROUTES + "*");
//        Collection keys = Collections.singleton(MAPLE_CLOUD_GATEWAY_ROUTES + "*");
//        List<String> gatewayKeys = stringRedisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(gatewayKeys)) {
            return;
        }

        List<String> gatewayRoutes = Optional.ofNullable(
                stringRedisTemplate.opsForValue().multiGet(gatewayKeys)).orElse(Lists.newArrayList());
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
