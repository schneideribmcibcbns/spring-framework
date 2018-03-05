package com.logicbig.example.typeconverter;

import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleTypeConverterExample2 {
    public static void main (String[] args) {
        SimpleTypeConverter tc = new SimpleTypeConverter();
        tc.registerCustomEditor(Date.class, new CustomDateEditor(
                            new SimpleDateFormat("yyyy-MMM-dd"), false));
        Date date = tc.convertIfNecessary("2015-JUN-10", Date.class);
        System.out.println(date);
    }

    private static class LocalBean {
        private Date date;
    }
}