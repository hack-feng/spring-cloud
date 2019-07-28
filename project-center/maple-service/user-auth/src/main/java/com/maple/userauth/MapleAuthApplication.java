package com.maple.userauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author
 */
@SpringBootApplication
@MapperScan("com.loyal.admin.auth.mapper")
public class MapleAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapleAuthApplication.class, args);
    }
}
