package com.zq.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EntityScan(basePackages = {"com.bawei.entity"})
@EnableCaching
public class SellerApp {
    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
