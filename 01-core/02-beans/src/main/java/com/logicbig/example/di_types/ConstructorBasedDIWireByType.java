package com.logicbig.example.di_types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration

public class ConstructorBasedDIWireByType {

    @Bean(name = "a")
    public OrderService orderServiceByProvider1 () {
        return new OrderServiceImpl1();
    }

    @Bean(name = "b")
    public OrderService orderServiceByProvider2 () {
        return new OrderServiceImpl2();
    }


    public static void main (String... strings) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        		ConstructorBasedDIWireByType.class);
        OrderServiceClient bean = context.getBean(ConstructorBasedDIWireByType.OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }

    
    
    @Component
    public static class OrderServiceClient {

        private final OrderService orderService1;
        private final OrderService orderService2;

        @Autowired
        OrderServiceClient (@Qualifier("a") OrderService orderService1,
                            @Qualifier("b") OrderService orderService2) {
            this.orderService1 = orderService1;
            this.orderService2 = orderService2;
        }

        public void showPendingOrderDetails () {
            System.out.println(orderService1.getOrderDetails("100"));
            System.out.println(orderService2.getOrderDetails("100"));
        }
    }
}