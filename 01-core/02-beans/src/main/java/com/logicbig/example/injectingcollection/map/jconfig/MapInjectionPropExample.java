package com.logicbig.example.injectingcollection.map.jconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MapInjectionPropExample {

    @Bean
    public TestBean testBean () {
        return new TestBean();
    }

    @Bean
    public Map<String, Integer> map () {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        return map;
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                MapInjectionPropExample.class);
        TestBean bean = context.getBean(TestBean.class);
        System.out.println(bean.getMap());
    }

    private static class TestBean {
        private Map<String, Integer> map;

        @Autowired
        public void setMap (Map<String, Integer> map) {
            this.map = map;
        }

        public Map<String, Integer> getMap () {
            return map;
        }
    }
}