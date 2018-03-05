package com.logicbig.example.beanutils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResolveSignature {

    public static void main (String[] args) throws
                        InvocationTargetException, IllegalAccessException {
        Method doSomething = BeanUtils.resolveSignature("aMethod(java.lang.String, int)",
                            LocalBean.class);
        doSomething.invoke(new LocalBean(), "some string value", 100);

        doSomething = BeanUtils.resolveSignature("aMethod(java.lang.Integer)",
                            LocalBean.class);
        doSomething.invoke(new LocalBean(), 200);
    }

    private static class LocalBean {
        public void aMethod (String str, int anInt) {
            System.out.println(str);
            System.out.println(anInt);
        }

        public void aMethod (Integer anInt) {
            System.out.println(anInt);
        }
    }
}