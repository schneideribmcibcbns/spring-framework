package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import com.logicbig.example.TestBean3;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;

public class BeanWrapperNestedIndexProperty {
    public static void main (String[] args) {
        TestBean3 testBean = new TestBean3();
        testBean.setTestBeans(new ArrayList<>());
        testBean.setTestBeans(0, new TestBean());
        testBean.setTestBeans(1, new TestBean());

        BeanWrapper bw2 = new BeanWrapperImpl(testBean);
        bw2.setPropertyValue("testBeans[0].aString", "aStr0");
        bw2.setPropertyValue("testBeans[0].anInt", "3");

        bw2.setPropertyValue("testBeans[1].aString", "aStr1");
        bw2.setPropertyValue("testBeans[1].anInt", "6");
        System.out.println(bw2.getWrappedInstance());
    }
}