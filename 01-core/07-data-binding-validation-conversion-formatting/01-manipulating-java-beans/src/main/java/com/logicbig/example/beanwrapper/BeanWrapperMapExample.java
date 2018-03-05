package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashMap;
import java.util.Map;


public class BeanWrapperMapExample {
    public static void main (String[] args) {
        BeanWrapperImpl bw = new BeanWrapperImpl(new TestBean());

        Map<String, Object> map = new HashMap<>();
        map.put("aString", "SomeString");
        map.put("anInt", "10");
        bw.setPropertyValues(map);
        System.out.println(bw.getWrappedInstance());
    }
}