package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import com.logicbig.example.TestBean2;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperNestedBean {
    public static void main (String[] args) {
        BeanWrapper bw2 = new BeanWrapperImpl(new TestBean2());
        bw2.setPropertyValue("anotherString", "stringVal");

        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValue("anInt", 3);
        bw.setPropertyValue("aString", "aStrVal");

        bw2.setPropertyValue("testBean", bw.getWrappedInstance());
        System.out.println(bw2.getWrappedInstance());
    }
}