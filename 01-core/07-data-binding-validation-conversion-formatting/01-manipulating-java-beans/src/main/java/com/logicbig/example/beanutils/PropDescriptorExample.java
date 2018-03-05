package com.logicbig.example.beanutils;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanUtils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;

public class PropDescriptorExample {
    public static void main (String[] args) throws IntrospectionException {
        PropertyDescriptor[] actual = Introspector.getBeanInfo(TestBean.class)
                                                  .getPropertyDescriptors();
        PropertyDescriptor[] descriptors = BeanUtils.getPropertyDescriptors(TestBean.class);

        System.out.println(Arrays.toString(actual));
        System.out.println(Arrays.toString(descriptors));
    }
}