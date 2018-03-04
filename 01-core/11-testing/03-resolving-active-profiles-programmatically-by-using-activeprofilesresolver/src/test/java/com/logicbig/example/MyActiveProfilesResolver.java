package com.logicbig.example;

import org.springframework.test.context.ActiveProfilesResolver;

public class MyActiveProfilesResolver  implements ActiveProfilesResolver{
    @Override
    public String[] resolve(Class<?> testClass) {
        String os = System.getProperty("os.name");
        String profile = (os.toLowerCase().startsWith("windows")) ? "windows" : "other";
        return new String[]{profile};
    }
}