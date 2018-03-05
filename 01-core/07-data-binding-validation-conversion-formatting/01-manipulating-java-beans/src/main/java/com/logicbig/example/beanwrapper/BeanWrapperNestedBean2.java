package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import com.logicbig.example.TestBean2;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperNestedBean2 {
    public static void main (String[] args) {
        TestBean2 testBean2 = new TestBean2();
        testBean2.setTestBean(new TestBean());

        BeanWrapper bw2 = new BeanWrapperImpl(testBean2);
        bw2.setPropertyValue("anotherString", "stringVal");
        bw2.setPropertyValue("testBean.aString", "aStr");
        bw2.setPropertyValue("testBean.anInt", 7);

        System.out.println(bw2.getWrappedInstance());

    }
}