package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebMvcConfigExample {

    public static void main (String[] args) {

        SpringApplication app =
                  new SpringApplication(WebMvcConfigExample.class);
        app.run(args);
    }
}