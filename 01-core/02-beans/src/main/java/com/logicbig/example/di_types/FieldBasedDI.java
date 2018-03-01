package com.logicbig.example.di_types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FieldBasedDI {

    @Bean
    public OrderService orderServiceByProvider1 () {
        return new OrderServiceImpl1();
    }

    @Bean
    public OrderServiceClient createClient () {
        return new OrderServiceClient();
    }

    public static void main (String... strings) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                            FieldBasedDI.class);
        OrderServiceClient bean = context.getBean(OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }

    private static class OrderServiceClient {

        @Autowired
        private OrderService orderService;

        public void showPendingOrderDetails () {
            System.out.println(orderService.getOrderDetails("100"));
        }
    }
}