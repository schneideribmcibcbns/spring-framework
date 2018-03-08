package com.logicbig.example;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ExampleExitCodeGenerator {

    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ExampleExitCodeGenerator.class, args);

        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();

        int exitValue = SpringApplication.exit(context);
        System.exit(exitValue);
    }

    private static class MyBean implements ExitCodeGenerator {

        public void doSomething() {
            System.out.println("in doSomething()");
        }

        @Override
        public int getExitCode() {
            return 500;
        }
    }
}