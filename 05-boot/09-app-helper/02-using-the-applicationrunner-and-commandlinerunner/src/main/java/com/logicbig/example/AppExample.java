package com.logicbig.example;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootConfiguration
public class AppExample {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    ApplicationRunner appRunner() {
        return new AppRunner();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(AppExample.class, args);
        System.out.println("Context ready : " + context);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class AppRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("running appRunner#run: " + Arrays.toString(args.getSourceArgs()));
        }
    }

    private static class MyBean {

        public void doSomething() {
            System.out.println("In a bean doing something");
        }
    }
}