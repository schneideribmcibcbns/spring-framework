package com.logicbig.example.propertyacessorfactory;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class PropertyAccessorFactoryExample {

    public static void main (String[] args) {
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(new TestBean());
        bw.setPropertyValue("aString", "anotherString");
        System.out.println(bw.getWrappedInstance());
    }
}