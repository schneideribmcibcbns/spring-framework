package com.logicbig.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExampleMain {
    private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(ExampleMain.class, args);
        logger.info("just a test info log");
    }
}