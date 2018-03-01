package com.logicbig.example.injectingcollection.collection.jconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
@ComponentScan(basePackageClasses = SetInjectionScanExample.class, useDefaultFilters = false,
                    includeFilters = {@ComponentScan.Filter(
                                        type = FilterType.ASSIGNABLE_TYPE,
                                        value = SetInjectionScanExample.class)})
public class SetInjectionScanExample {

    @Bean
    public Set<String> strSet () {
        return new HashSet<>(Arrays.asList("two hundred", "three hundred", "four hundred"));
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(SetInjectionScanExample.class);
        TestBean bean = context.getBean(TestBean.class);
        System.out.println(bean.getStrSet());
    }


    @Component
    static class TestBean {
        private final Set<String> strSet;

        //@Autowired  Spring 4.3 and later doesn't required the annotation
        TestBean (Set<String> strSet) {
            this.strSet = strSet;
        }

        public Set<String> getStrSet () {
            return strSet;
        }
    }
}