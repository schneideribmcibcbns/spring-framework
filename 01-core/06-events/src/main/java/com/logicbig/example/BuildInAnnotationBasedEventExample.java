package com.logicbig.example;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.*;

@Configuration
public class BuildInAnnotationBasedEventExample {

    @Bean
    AListenerBean listenerBean () {
        return new AListenerBean();
    }

    public static void main (String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                            BuildInAnnotationBasedEventExample.class);
        System.out.println("-- stopping context --");
        context.stop();
        System.out.println("-- starting context --");
        context.start();
        System.out.println("-- closing context --");
        context.close();
    }

    private static class AListenerBean {

        @EventListener
        public void handleContextRefreshed (ContextRefreshedEvent event) {
            System.out.print("context refreshed event fired: ");
            System.out.println(event);
        }

        @EventListener
        public void handleContextStarted (ContextStartedEvent event) {
            System.out.print("context started event fired: ");
            System.out.println(event);
        }

        @EventListener
        public void handleContextStopped (ContextStoppedEvent event) {
            System.out.print("context stopped event fired: ");
            System.out.println(event);
        }

        @EventListener
        public void handleContextClosed (ContextClosedEvent event) {
            System.out.print("context  closed event fired: ");
            System.out.println(event);
        }

    }
}