package com.logicbig.example.beanwrapper;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanWrapperReadOnlyCheckExample {
    public static void main (String[] args) {
        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValue("aString", "someString");
        System.out.println("date read only: " + bw.isReadableProperty("date"));
        //similarly we have bw.isWritableProperty(..) method
        System.out.println(bw.getWrappedInstance());
    }
}