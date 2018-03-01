package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigPath {

    @Bean
    public UserControllerPath myMvcController() {
        return new UserControllerPath();
    }
}