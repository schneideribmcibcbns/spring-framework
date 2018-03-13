package com.logicbig.example;


import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Collection;
import java.util.Currency;

public class ConversionServiceExample {
    public static void main (String[] args) {
        DefaultConversionService service = new DefaultConversionService();

        Currency convert = service.convert("USD", Currency.class);
        System.out.println(convert);

        Collection<String> list = service.convert("Deb, Mike, Kim",
                                                        Collection.class);
        System.out.println(list);
    }
}