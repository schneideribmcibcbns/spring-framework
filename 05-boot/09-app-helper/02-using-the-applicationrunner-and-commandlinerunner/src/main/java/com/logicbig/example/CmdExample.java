package com.logicbig.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootConfiguration
public class CmdExample {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    @Bean
    CommandLineRunner cmdRunner() {
        return new CmdRunner();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(CmdExample.class, args);
        System.out.println("Context ready : " + context);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class CmdRunner implements CommandLineRunner {

        @Override
        public void run(String... strings) throws Exception {
            System.out.println("running CmdRunner#run: " + Arrays.toString(strings));
        }
    }

    private static class MyBean {

        public void doSomething() {
            System.out.println("In a bean doing something");
        }
    }
}