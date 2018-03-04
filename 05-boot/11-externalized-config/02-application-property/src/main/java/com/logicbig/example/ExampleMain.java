package com.logicbig.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication bootApp = new SpringApplication(ExampleMain.class);
        bootApp.setBannerMode(Banner.Mode.OFF);
        bootApp.setLogStartupInfo(false);
        ConfigurableApplicationContext context = bootApp.run(args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.startApplication();
    }

    private static class MyBean {

        @Value("${app.title}")
        private String appTitle;

        public void startApplication() {
            System.out.printf("-- in MyBean app title: %s --%n", appTitle);
        }
    }
}