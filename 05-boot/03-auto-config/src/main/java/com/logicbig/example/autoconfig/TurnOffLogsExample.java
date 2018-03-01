package com.logicbig.example.autoconfig;

import org.springframework.boot.SpringApplication;

import java.net.MalformedURLException;

public class TurnOffLogsExample {
    public static void main (String[] args) throws MalformedURLException {
        SpringApplication app = new SpringApplication(TurnOffLogsExample.class);
        app.setLogStartupInfo(false);
        app.run(args);
    }
}