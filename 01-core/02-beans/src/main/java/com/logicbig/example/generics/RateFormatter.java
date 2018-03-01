package com.logicbig.example.generics;


import java.math.BigDecimal;
import java.text.NumberFormat;

public class RateFormatter<T extends Number> {

    public String format(T number){
        NumberFormat format = NumberFormat.getInstance();
        if(number instanceof Integer){
            format.setMinimumIntegerDigits(0);
        }else if(number instanceof BigDecimal){
            format.setMinimumIntegerDigits(2);
            format.setMaximumFractionDigits(2);
        }//others

        return format.format(number);
    }
}