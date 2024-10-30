package com.ak.bkdprocess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EntityScan("com.ak.common.entity")
@SpringBootApplication(scanBasePackages = {"com.ak.common.mapper","com.ak.bkdprocess"})
@EnableJpaRepositories
public class BackendProcessMain {
  public static void main(String[] args) {
    SpringApplication.run(BackendProcessMain.class, args);
  }
}