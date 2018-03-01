package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class WebMvcConfigExample extends SpringBootServletInitializer {
	
   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       return builder.sources(WebMvcConfigExample.class);
   }
   
    public static void main (String[] args) {

        SpringApplication app =
                  new SpringApplication(WebMvcConfigExample.class);
        app.run(args);
    }
}