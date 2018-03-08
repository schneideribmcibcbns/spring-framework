package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        //no need to call context.registerShutdownHook();
    }

    private static class MyBean {

        @PostConstruct
        public void init() {
            System.out.println("init");
        }

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("destroy");
        }
    }
}