package com.maple.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhua
 * @date 2019/9/23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MapleJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleJobApplication.class, args);
    }

}
