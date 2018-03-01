package com.logicbig.example.autoconfig;

import org.springframework.boot.ImageBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;

public class ImageBannerExample {
    public static void main (String[] args) throws MalformedURLException {
        SpringApplication app = new SpringApplication(ImageBannerExample.class);
        UrlResource r = new UrlResource("http://www.logicbig.com/tutorials/" +
                                                  "spring-framework/spring-boot/" +
                                                  "images/spring-boot.png");
        app.setBanner(new ImageBanner(r));
        app.setLogStartupInfo(false);
        app.run(args);
    }
}