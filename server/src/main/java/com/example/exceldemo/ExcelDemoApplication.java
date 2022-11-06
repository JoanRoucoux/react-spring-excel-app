package com.example.exceldemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ExcelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExcelDemoApplication.class, args);
    }

}
