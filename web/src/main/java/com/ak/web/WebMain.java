package com.ak.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ak.common.entity")
public class WebMain {
    public static void main(String[] args) {
        SpringApplication.run(WebMain.class, args);
    }
}