package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

public class BeanWrapperExample {
    public static void main (String[] args) {
        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValue("aString", "someString");
        PropertyValue pv = new PropertyValue("anInt", 3);
        //the next commented line will also work
        /*PropertyValue pv = new PropertyValue("anInt", "3");*/
        bw.setPropertyValue(pv);
        System.out.println(bw.getWrappedInstance());

    }
}