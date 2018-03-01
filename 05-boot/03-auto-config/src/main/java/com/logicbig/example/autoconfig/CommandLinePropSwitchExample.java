package com.logicbig.example.autoconfig;

import org.springframework.boot.SpringApplication;

import java.net.MalformedURLException;

public class CommandLinePropSwitchExample {
    public static void main (String[] args) throws MalformedURLException {

        SpringApplication app = new SpringApplication(CommandLinePropSwitchExample.class);
        app.setLogStartupInfo(false);
        //rename resources/my-banner.txt to banner.txt
        //here we are just using command line arg
        String[] strings = {"--banner.location=my-banner.txt"};
        app.run(strings);
    }
}