package com.logicbig.example.componentscanfiltering;

import org.springframework.context.ApplicationContext;

import java.util.Arrays;

public class Util {
   public static void printBeanNames(ApplicationContext context){
       String[] beanNames = context.getBeanDefinitionNames();
       Arrays.stream(beanNames)
             .filter(n -> !n.contains("springframework"))
             .forEach(System.out::println);
   }
}