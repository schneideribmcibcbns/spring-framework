package com.logicbig.example.app;

import com.logicbig.example.client.HelloWorldServiceClient;
import com.logicbig.example.service.HelloWorldService;
import com.logicbig.example.service.impl.HelloWorldServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppRunner {

    @Bean
    public HelloWorldService createHelloWorldService() {
        return new HelloWorldServiceImpl();
    }

    @Bean
    public HelloWorldServiceClient createClient() {
        return new HelloWorldServiceClient();
    }

    public static void main(String... strings) {
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppRunner.class);
        HelloWorldServiceClient bean = context.getBean(HelloWorldServiceClient.class);
        bean.showMessage();
        
    }
}
