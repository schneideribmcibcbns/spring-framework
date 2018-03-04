package com.logicbig.example;

import javax.validation.constraints.Pattern;

public class PhoneNumber {
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    private String value;
    @Pattern(regexp = "(?i)cell|house|work")
    private String type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}