package com.logicbig.example.autoconfig;

import org.springframework.boot.SpringApplication;

import java.net.MalformedURLException;
import java.util.Properties;

public class SetDefaultPropertiesExample {
    public static void main (String[] args) throws MalformedURLException {
        SpringApplication app = new SpringApplication(CustomBannerExample.class);
        Properties p = new Properties();
        p.setProperty("banner.location", "my-banner.txt");
        p.setProperty("logging.level.org.springframework", "DEBUG");
        app.setDefaultProperties(p);
        app.run(args);
    }
}