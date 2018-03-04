package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ExampleMain.class, args);

        String[] profiles = context.getEnvironment().getActiveProfiles();
        System.out.println("Active Profiles: "+ Arrays.toString(profiles));

        MyBean myBean = context.getBean(MyBean.class);
        myBean.doSomething();
    }

    private static class MyBean {

        @Value("${refresh.rate}")
        private int refreshRate;

        public void doSomething() {
            System.out.printf("Refresh Rate : %s%n", refreshRate);
        }
    }
}