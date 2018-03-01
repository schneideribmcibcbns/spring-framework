package com.logicbig.example.injectingcollection.array.jconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackageClasses = ArrayQualifierInjectionScanExample.class,
                    useDefaultFilters = false,
                    includeFilters = {@ComponentScan.Filter(
                                        type = FilterType.ASSIGNABLE_TYPE,
                                        value = ArrayQualifierInjectionScanExample.TestBean.class)})
public class ArrayQualifierInjectionScanExample {

    @Bean
    public String[] strArray () {
        return new String[]{"two", "three", "four"};
    }

    @Bean(name = "myArray")
    public String[] strArray2 () {
        return new String[]{"five", "six", "seven"};
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                ArrayQualifierInjectionScanExample.class);
        TestBean bean = context.getBean(TestBean.class);
        System.out.println(Arrays.toString(bean.getStringArray()));
    }

    @Component
    static class TestBean {
        private final String[] stringArray;

        public TestBean (@Qualifier("myArray") String[] stringArray) {
            this.stringArray = stringArray;
        }

        public String[] getStringArray () {
            return stringArray;
        }
    }
}