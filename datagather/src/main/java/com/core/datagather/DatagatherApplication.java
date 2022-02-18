package com.core.datagather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatagatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatagatherApplication.class, args);
    }

}
