package com.logicbig.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExampleMain {
    private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);
    public static void main(String[] args) throws InterruptedException {

        ConfigurableApplicationContext context =
                SpringApplication.run(ExampleMain.class, args);
        logger.warn("warning msg");
        logger.error("error msg");
    }
}