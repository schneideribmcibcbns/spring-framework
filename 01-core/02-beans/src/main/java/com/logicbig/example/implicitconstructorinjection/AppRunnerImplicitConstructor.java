package com.logicbig.example.implicitconstructorinjection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.logicbig.example.implicitconstructorinjection"})
public class AppRunnerImplicitConstructor {

    public static void main (String... strings) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                AppRunnerImplicitConstructor.class);
        OrderServiceClient bean = context.getBean(OrderServiceClient.class);
        bean.showPendingOrderDetails();
    }


}