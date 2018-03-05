package com.logicbig.example.beanutils;

import com.logicbig.example.TestBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class CopyPropsToDifferentType {
    public static void main (String[] args) {
        BeanWrapper bw = new BeanWrapperImpl(new TestBean());
        bw.setPropertyValue("aString", "someString");
        bw.setPropertyValue("anInt", 3);

        TestBeanDifferent testBean2 = new TestBeanDifferent();

        //only properties of same name will be copied
        BeanUtils.copyProperties(bw.getWrappedInstance(), testBean2);

        System.out.println(testBean2);
    }

    private static class TestBeanDifferent {
        private String aString;
        private int differentInt;

        public String getAString () {
            return aString;
        }

        public void setAString (String aString) {
            this.aString = aString;
        }

        public int getDifferentInt () {
            return differentInt;
        }

        public void setDifferentInt (int differentInt) {
            this.differentInt = differentInt;
        }

        @Override
        public String toString () {
            return "TestBeanDifferent{" +
                                "aString='" + aString + '\'' +
                                ", differentInt=" + differentInt +
                                '}';
        }
    }
}