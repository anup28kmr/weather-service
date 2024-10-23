package com.ak.bkdprocess;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan("com.ak.common.entity")
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}