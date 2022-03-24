package com.skyestock.quickfix.fixserver;

import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.skyestock.service")
public class FixserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(FixserverApplication.class, args);
    }

}
