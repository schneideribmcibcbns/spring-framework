package com.logicbig.example.componentscanfiltering;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = MyAnnotation.class)})
public class FilterTypeAnnotationExample {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(FilterTypeAnnotationExample.class);
        Util.printBeanNames(context);
    }
}