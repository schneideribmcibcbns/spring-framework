package com.logicbig.example.autoconfig;

import org.springframework.boot.SpringApplication;

import java.net.MalformedURLException;

public class CommandLineAppPropsSwitchExample {
    public static void main (String[] args) throws MalformedURLException {
        SpringApplication app = new SpringApplication(CustomBannerExample.class);
        app.setLogStartupInfo(false);
        //rename my-application.properties to application.properties
        //or use a command line switch.. here we just overriding args
        String[] myArgs = {"--spring.config.name=my-application"};
        app.run(myArgs);
    }
}