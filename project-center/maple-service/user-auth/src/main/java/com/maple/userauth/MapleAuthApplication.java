package com.maple.userauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MapleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleAuthApplication.class, args);
    }
}
