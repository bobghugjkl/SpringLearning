package com.xxxx.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

@SpringBootApplication
public class StartApplication {
    private static Logger logger = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
        logger.info("SpringBoot 应用开始启动...");
        SpringApplication.run(StartApplication.class);
    }
}
