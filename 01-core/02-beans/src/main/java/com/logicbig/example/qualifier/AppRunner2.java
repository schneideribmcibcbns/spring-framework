package com.logicbig.example.qualifier;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppRunner2 {

    @Bean
    @Qualifier("OrderServiceA")
    public OrderService orderServiceByProvider1() {
        return new OrderServiceImpl1();
    }

    @Bean(name = "OrderServiceB")
    public OrderService orderServiceByProvider2() {
        return new OrderServiceImpl2();
    }

    @Bean
    public OrderServiceClient createClient() {
        return new OrderServiceClient();
    }

    public static void main(String... strings) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppRunner2.class);
        OrderServiceClient bean = context.getBean(OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }
}