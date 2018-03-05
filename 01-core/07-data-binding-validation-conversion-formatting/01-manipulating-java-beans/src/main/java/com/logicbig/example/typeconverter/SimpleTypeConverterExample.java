package com.logicbig.example.typeconverter;

import org.springframework.beans.SimpleTypeConverter;

public class SimpleTypeConverterExample {
    public static void main (String[] args) {
        SimpleTypeConverter tc = new SimpleTypeConverter();
        Double d = tc.convertIfNecessary("345", Double.class);
        System.out.println(d);
    }
}