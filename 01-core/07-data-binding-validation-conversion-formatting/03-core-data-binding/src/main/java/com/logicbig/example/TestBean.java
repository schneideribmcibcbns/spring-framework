package com.logicbig.example;

public class TestBean {
    private int anInt;

    public int getAnInt () {
        return anInt;
    }

    public void setAnInt (int anInt) {
        this.anInt = anInt;
    }

    @Override
    public String toString () {
        return "TestBean{anInt=" + anInt + '}';
    }
}