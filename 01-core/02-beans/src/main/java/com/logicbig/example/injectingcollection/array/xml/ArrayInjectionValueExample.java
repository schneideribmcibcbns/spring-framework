package com.logicbig.example.injectingcollection.array.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class ArrayInjectionValueExample {
    public static void main (String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Config-value.xml", ArrayInjectionValueExample.class);
        TestBean bean = context.getBean(TestBean.class);
        System.out.println(Arrays.toString(bean.getStringArray()));
    }

    private static class TestBean{
        private final String[] stringArray;

        public TestBean (String[] stringArray) {
            this.stringArray = stringArray;
        }

        public String[] getStringArray () {
            return stringArray;
        }
    }
}