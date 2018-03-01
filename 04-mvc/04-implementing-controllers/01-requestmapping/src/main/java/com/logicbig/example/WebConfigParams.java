package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigParams {

    @Bean
    public UserControllerParams myMvcController() {
        return new UserControllerParams();
    }
}