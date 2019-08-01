package com.maple.cloud.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CloudManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudManageApplication.class, args);
    }

}
