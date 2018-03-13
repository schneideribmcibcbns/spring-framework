package com.logicbig.example;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

public class DefaultFormatterListExample {
    public static void main (String[] args) {

        ConversionService service = new DefaultConversionService();
        System.out.println("----- list for DefaultConversionService -----");
        System.out.println(service);

        service = new DefaultFormattingConversionService();
        System.out.println("----- list for DefaultFormattingConversionService -----");
        System.out.println(service);

        service = new FormattingConversionService();
        System.out.println("----- list for FormattingConversionService -----");
        System.out.println(service);


    }
}