package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class WebConfigConsume {

    @Bean
    public UserControllerConsume myMvcController() {
        return new UserControllerConsume();
    }
}