package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigExtended {
    @Bean
    public OrderService orderService() {
        return new OrderService(){
            @Override
            public String getOrdersForCustomer(String customerId) {
                return "orders for customer " + customerId;
            }
        };
    }
}