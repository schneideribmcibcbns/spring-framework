package com.logicbig.example.generics;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class RateCalculator {

    @Autowired
    private RateFormatter<BigDecimal> formatter;

    public void calculate() {
        BigDecimal rate = new BigDecimal(Math.random() * 100);
        System.out.println(formatter.format(rate));
    }
}