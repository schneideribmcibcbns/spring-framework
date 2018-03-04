package com.logicbig.example;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Component
@ConfigurationProperties("app")
@Validated
public class MyAppProperties {
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    private String adminContactNumber;
    @Min(1)
    private int refreshRate;

    public String getAdminContactNumber() {
        return adminContactNumber;
    }

    public void setAdminContactNumber(String adminContactNumber) {
        this.adminContactNumber = adminContactNumber;
    }

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                "adminContactNumber='" + adminContactNumber + '\'' +
                ", refreshRate=" + refreshRate +
                '}';
    }
}