package com.maple.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipkinMasterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinMasterApplication.class, args);
    }

}
