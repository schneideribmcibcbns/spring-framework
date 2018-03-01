package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigRegex {

    @Bean
    public UserControllerRegex myMvcController() {
        return new UserControllerRegex();
    }
}