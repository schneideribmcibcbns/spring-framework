package com.logicbig.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.Map;


public class DefaultSystemSourcesExample {
    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        printSources(env.getPropertySources());
        System.out.println("---- System properties -----");
        printMap(env.getSystemProperties());
        System.out.println("---- System Env properties -----");
        printMap(env.getSystemEnvironment());
    }

    private static void printSources (PropertySources propertySources) {
        System.out.println("---- property sources ----");
        for (PropertySource<?> propertySource : propertySources) {
            System.out.println("name =  " + propertySource.getName() + "\nsource = " + propertySource
                                .getSource().getClass()+"\n");
        }

    }

    
    private static void printMap (Map<?, ?> map) {
        map.entrySet()
           .stream()
           .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

    }
    
}