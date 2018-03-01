package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfigHeader {

    @Bean
    public UserControllerHeader myMvcController() {
        return new UserControllerHeader();
    }
}