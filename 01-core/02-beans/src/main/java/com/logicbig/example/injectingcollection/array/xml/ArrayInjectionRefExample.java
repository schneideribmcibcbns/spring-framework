package com.logicbig.example.injectingcollection.array.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class ArrayInjectionRefExample {

    public static void main (String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config-ref.xml", ArrayInjectionRefExample.class);
        TestBean bean = context.getBean(TestBean.class);
        System.out.println(Arrays.toString(bean.getRefBean()));
    }

    private static class TestBean {
        private final RefBean[] refBean;

        private TestBean (RefBean[] refBean) {
            this.refBean = refBean;
        }

        public RefBean[] getRefBean () {
            return refBean;
        }
    }

    private static class RefBean {
        private String str;

        public String getStr () {
            return str;
        }

        public void setStr (String str) {
            this.str = str;
        }

        @Override
        public String toString () {
            return "RefBean{" +
                                "str='" + str + '\'' +
                                '}';
        }
    }
}