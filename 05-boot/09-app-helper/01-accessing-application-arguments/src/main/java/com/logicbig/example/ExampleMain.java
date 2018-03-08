package com.logicbig.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

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
    }

    private static class MyBean {
        @Autowired
        ApplicationArguments appArgs;

        public void doSomething() {
            List<String> args = appArgs.getOptionValues("myArg");
            for (int i=0; i<args.size(); i++) {
                System.out.printf("The value of application arg myArg: %s%n", args.get(i));
            }
        }
    }
}