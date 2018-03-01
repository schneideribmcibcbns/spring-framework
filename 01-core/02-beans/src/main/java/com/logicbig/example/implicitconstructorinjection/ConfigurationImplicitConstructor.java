package com.logicbig.example.implicitconstructorinjection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;



@Configuration
@ComponentScan({"com.logicbig.example.implicitconstructorinjection"})
public class ConfigurationImplicitConstructor {

    private final OrderService orderService;

    public ConfigurationImplicitConstructor (OrderService orderService) {
        this.orderService = orderService;
    }

    @Bean(name = "services")
    public List<OrderService> services(){
        return Arrays.asList(orderService);
    }


    public static void main (String... strings) {
       AnnotationConfigApplicationContext context =
                           new AnnotationConfigApplicationContext(
                                               ConfigurationImplicitConstructor.class);
       Object services = context.getBean("services");
       System.out.println(services);
   }


}