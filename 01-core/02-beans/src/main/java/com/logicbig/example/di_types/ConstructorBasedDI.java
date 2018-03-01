package com.logicbig.example.di_types;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorBasedDI {

    @Bean
    public OrderService orderServiceByProvider1 () {
        return new OrderServiceImpl1();
    }

    @Bean
    public OrderServiceClient createClient () {
        return new OrderServiceClient(orderServiceByProvider1());
    }

    public static void main (String... strings) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
        		ConstructorBasedDI.class);
        OrderServiceClient bean = context.getBean(OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }

    private static class OrderServiceClient {

        private OrderService orderService;

       // @Autowired is not needed
        OrderServiceClient (OrderService orderService) {
            this.orderService = orderService;
        }

        public void showPendingOrderDetails () {
            System.out.println(orderService.getOrderDetails("100"));
        }
    }
}