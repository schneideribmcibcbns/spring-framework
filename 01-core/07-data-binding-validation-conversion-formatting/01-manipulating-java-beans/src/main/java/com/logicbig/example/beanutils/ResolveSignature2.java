package com.logicbig.example.beanutils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolveSignature2 {

    public static void main (String[] args) throws
                        InvocationTargetException, IllegalAccessException {
        Method doSomething = BeanUtils.resolveSignature("doSomething",
                            LocalBean.class);
        doSomething.invoke(new LocalBean());
    }

    private static class LocalBean {
        public void doSomething () {
            System.out.println("-- doing something -- ");

        }
    }
}