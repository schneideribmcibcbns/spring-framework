package com.logicbig.example.beanutils;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyPropsToSameType {
    public static void main (String[] args) {
        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValue("aString", "someString");
        bw.setPropertyValue("anInt", 3);

        TestBean testBean2 = new TestBean();

        BeanUtils.copyProperties(bw.getWrappedInstance(), testBean2);

        System.out.println(testBean2);
    }
}