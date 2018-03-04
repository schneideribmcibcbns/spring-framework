package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {
    @Bean
    @Profile("windows")
    public MyService myServiceA() {
        return new MyServiceA();
    }

    @Bean
    @Profile("other")
    public MyService myServiceB() {
        return new MyServiceB();
    }
}