package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
public class MyWebConfig {

    @Bean
    public UserController userController () {
        return new UserController();
    }

    @Bean
    public UserService userService(){
        return new InMemoryUserService();
    }
}