package com.logicbig.example;

import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyEditor;
import java.util.Currency;

public class DefaultEditorsTest {

    public static void main (String[] args) {
        BeanWrapperImpl wrapper = new BeanWrapperImpl();

        PropertyEditor editor = wrapper.getDefaultEditor(Currency.class);
        editor.setAsText("MYR");
        Currency value = (Currency) editor.getValue();
        System.out.println(value.getDisplayName());

    }
}