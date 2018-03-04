package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherAppConfig {
    @Bean
    public CustomerService customerService() {
        return new CustomerService() {
            @Override
            public String getCustomerById(String id) {
                return "Other Customer " + id;
            }
        };
    }
}