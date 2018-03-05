package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.MutablePropertyValues;

public class BeanWrapperMutablePropertyExample {
    public static void main (String[] args) {
        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("aString", "SomeString");
        mpv.add("anInt", "10");

        bw.setPropertyValues(mpv);
        System.out.println(bw.getWrappedInstance());
    }
}