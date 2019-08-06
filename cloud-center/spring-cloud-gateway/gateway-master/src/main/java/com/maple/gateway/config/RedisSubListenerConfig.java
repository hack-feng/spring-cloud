package com.maple.gateway.config;

import com.maple.gateway.routes.RedisRouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSubListenerConfig {

    // 路由信息发布订阅的redis新增修改信道
    public static final String MAPLE_CLOUD_GATEWAY_ROUTES_UPDATE = "maple_cloud_gateway_routes_update";
    // 路由信息发布订阅的redis删除信道
    public static final String MAPLE_CLOUD_GATEWAY_ROUTES_DELETE = "maple_cloud_gateway_routes_delete";

    /**
     * 消息适配器
     *
     * 绑定消息监听者和接收监听的方法,必须要注入这个监听器，不然会报错
     * @return MessageListenerAdapter
     */
    @Bean
    public MessageListenerAdapter listenerAdapterSave() {
        return new MessageListenerAdapter(new RedisRouteDefinitionRepository(), "saveRouteDefinition");
    }

    @Bean
    public MessageListenerAdapter listenerAdapterDelete() {
        return new MessageListenerAdapter(new RedisRouteDefinitionRepository(), "deleteRouteDefinition");
    }

    /**
     *
     * @param connectionFactory
     * @param listenerAdapterSave
     * @param listenerAdapterDelete
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapterSave,
                                                   MessageListenerAdapter listenerAdapterDelete) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.addMessageListener(listenerAdapterSave, new PatternTopic(MAPLE_CLOUD_GATEWAY_ROUTES_UPDATE));
        listenerContainer.addMessageListener(listenerAdapterDelete, new PatternTopic(MAPLE_CLOUD_GATEWAY_ROUTES_DELETE));
        return listenerContainer;
    }
}
