package com.ak.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ak.common.entity")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}