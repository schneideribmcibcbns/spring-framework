package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.startApplication();
    }

    private static class MyBean {

        @Value("${app.title}")
        private String appTitle;

        public void startApplication() {
            System.out.printf("-- running application: %s --%n", appTitle);

        }
    }
}