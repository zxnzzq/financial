package com.zq.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.zq.entity"})
public class ManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class);
    }
}
