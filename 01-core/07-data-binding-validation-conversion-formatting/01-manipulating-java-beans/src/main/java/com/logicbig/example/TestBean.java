package com.logicbig.example;

import java.util.Date;

public class TestBean {
    private String aString;
    private int anInt = 5;
    private Date date = new Date();

    public String getAString () {
        return aString;
    }

    public void setAString (String aString) {
        this.aString = aString;
    }

    public int getAnInt () {
        return anInt;
    }

    public void setAnInt (int anInt) {
        this.anInt = anInt;
    }

    public Date getDate () {
        return date;
    }

    @Override
    public String toString () {
        return "TestBean{aString='" + aString + '\'' +
                            ", anInt=" + anInt +
                            ", date=" + date +
                            '}';
    }
}