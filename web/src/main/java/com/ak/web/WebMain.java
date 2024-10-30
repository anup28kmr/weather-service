package com.ak.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"com.ak.bkdprocess.service", "com.ak.web", "com.ak.common"})
@EntityScan("com.ak.common.entity")
@EnableJpaRepositories(basePackages = "com.ak.bkdprocess.repository")
public class WebMain {
  public static void main(String[] args) {
    SpringApplication.run(WebMain.class, args);
  }
}