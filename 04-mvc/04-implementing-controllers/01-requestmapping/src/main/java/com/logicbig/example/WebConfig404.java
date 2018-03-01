package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig404 {

    @Bean
    public UserController404 myMvcController() {
        return new UserController404();
    }
}