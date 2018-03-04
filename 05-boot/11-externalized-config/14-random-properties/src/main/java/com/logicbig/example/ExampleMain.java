package com.logicbig.example;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ExampleMain {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication springApplication = new SpringApplication(ExampleMain.class);
        springApplication.setBannerMode(Banner.Mode.OFF);
        springApplication.setLogStartupInfo(false);
        ConfigurableApplicationContext context = springApplication.run(args);
        MyAppProperties bean = context.getBean(MyAppProperties.class);
        System.out.println(bean);
    }
}