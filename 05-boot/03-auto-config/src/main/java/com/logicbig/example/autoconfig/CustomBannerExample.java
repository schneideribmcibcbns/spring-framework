package com.logicbig.example.autoconfig;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;
import java.net.MalformedURLException;

public class CustomBannerExample {
    public static void main (String[] args) throws MalformedURLException {
        SpringApplication app = new SpringApplication(CustomBannerExample.class);
        app.setLogStartupInfo(false);
        app.setBanner(new Banner() {
            @Override
            public void printBanner (Environment environment,
                                     Class<?> sourceClass,
                                     PrintStream out) {
                out.println("--- my custom banner ---");
            }
        });
        app.run(args);
    }
}