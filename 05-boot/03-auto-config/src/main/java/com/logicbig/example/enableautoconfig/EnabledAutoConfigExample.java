package com.logicbig.example.enableautoconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class EnabledAutoConfigExample {

    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    @Bean
    public MyWebController controller () {
        return new MyWebController();
    }

    public static void main (String[] args) {
        SpringApplication.run(EnabledAutoConfigExample.class, args);
    }
}