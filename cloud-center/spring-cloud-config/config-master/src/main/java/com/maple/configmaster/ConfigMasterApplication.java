package com.maple.configmaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer     //配置中心的服务端
@EnableDiscoveryClient  //开启eureka服务注册
public class ConfigMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigMasterApplication.class, args);
    }

}
