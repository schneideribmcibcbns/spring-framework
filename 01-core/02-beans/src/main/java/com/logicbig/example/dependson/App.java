package com.logicbig.example.dependson;

import org.springframework.context.annotation.*;

@Configuration
//@ComponentScan("com.logicbig.example")
public class App{

    @Bean(initMethod = "initialize")
    @DependsOn("eventListener")
    public EventPublisher eventPublisherBean () {
        return new EventPublisher();
    }

    @Bean(name = "eventListener", initMethod = "initialize")
    // @Lazy
    public EventListener eventListenerBean () {
        return new EventListener();
    }

    public static void main (String... strings) {
        new AnnotationConfigApplicationContext(App.class);
    }
}