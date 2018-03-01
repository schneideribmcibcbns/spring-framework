package com.logicbig.example.circulardependencies;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

@ComponentScan(basePackageClasses = ExampleMainFix2.class, useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = {ExampleMainFix2.BeanB.class, ExampleMainFix2.BeanA.class})})
@Configuration
public class ExampleMainFix2 {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(
                        ExampleMainFix2.class);
        BeanA beanA = context.getBean(BeanA.class);
        beanA.doSomething();
    }

    @Component
    static class BeanA {
        private final BeanB beanB;
        BeanA(@Lazy BeanB b) {
            this.beanB = b;
        }

        public void doSomething() {
            beanB.doSomething();
        }
    }

    @Component
    static class BeanB {
        private final BeanA beanA;
        BeanB(BeanA beanA) {
            this.beanA = beanA;
        }

        public void doSomething() {
            System.out.println("doing something");
        }
    }
}