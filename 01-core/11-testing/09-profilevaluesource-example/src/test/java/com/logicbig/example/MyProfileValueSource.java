package com.logicbig.example;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.annotation.ProfileValueSource;

import java.io.IOException;
import java.util.Properties;

public class MyProfileValueSource implements ProfileValueSource {
    private final Properties testProps;

    public MyProfileValueSource() {

        ClassPathResource resource = new ClassPathResource("test.properties");
        if (resource.exists()) {
            try {
                this.testProps = PropertiesLoaderUtils.loadProperties(resource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            testProps = new Properties();
        }
    }

    @Override
    public String get(String key) {
        return testProps.getProperty(key, System.getProperty(key));
    }
}