package com.logicbig.example.beaninfofactory;

import org.springframework.beans.ExtendedBeanInfoFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class BeanInfoFactoryExample {
    public static void main (String[] args) throws IntrospectionException {
        System.out.println("-------- using JDK Introspector -");
        useIntrospector();
        System.out.println("-------- using Spring BeanInfoFactory -");
        useBeanInfoFactory();
    }

    private static void useBeanInfoFactory () throws IntrospectionException {
        ExtendedBeanInfoFactory factory = new ExtendedBeanInfoFactory();
        BeanInfo beanInfo = factory.getBeanInfo(MyBean.class);
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            System.out.println(propertyDescriptor);
        }
    }

    private static void useIntrospector () throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(MyBean.class);
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            System.out.println(propertyDescriptor);
        }
    }

    private static class MyBean {
        private String name;

        public String getName () {
            return name;
        }

        public String setName (String str) {
            this.name = "MyBean-" + str;
            return name;
        }
    }
}