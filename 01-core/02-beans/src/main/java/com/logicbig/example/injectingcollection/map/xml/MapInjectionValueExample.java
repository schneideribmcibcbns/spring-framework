package com.logicbig.example.injectingcollection.map.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;

public class MapInjectionValueExample {
    public static void main (String[] args) {
        ApplicationContext context = new
                            ClassPathXmlApplicationContext("Config-value.xml",
                                                           MapInjectionValueExample.class);

        TestBean bean = context.getBean(TestBean.class);
        System.out.println(bean.getMap());
    }

    private static class TestBean {
        private final Map<String, Integer> map;

        private TestBean (Map<String, Integer> map) {
            this.map = map;
        }

        public Map<String, Integer> getMap () {
            return map;
        }
    }
}