package com.logicbig.example;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class MyApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext ac) {
        String os = System.getProperty("os.name");
        String profile = (os.toLowerCase().startsWith("windows")) ? "windows" : "other";
        ConfigurableEnvironment ce = ac.getEnvironment();
        ce.addActiveProfile(profile);
    }
}