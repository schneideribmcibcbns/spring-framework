package com.logicbig.example.directfieldAccessor;

import org.springframework.beans.DirectFieldAccessor;

public class DirectFieldAccessorExample {

    public static void main (String[] args) {
        DirectFieldAccessor a = new DirectFieldAccessor(new LocalBean());
        a.setPropertyValue("str", "setting a direct value");
        System.out.println(a.getWrappedInstance());
    }

    private static class LocalBean {
        private String str;

        @Override
        public String toString () {
            return "LocalBean{" +
                                "str='" + str + '\'' +
                                '}';
        }
    }
}