package com.logicbig.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties("app")
@Validated
public class MyAppProperties {

    @NotNull
    @Valid
    private MainScreenProperties mainScreenProperties;

    @NotNull
    @Valid
    private PhoneNumber adminContactNumber;

    public MainScreenProperties getMainScreenProperties() {
        return mainScreenProperties;
    }

    public void setMainScreenProperties(MainScreenProperties mainScreenProperties) {
        this.mainScreenProperties = mainScreenProperties;
    }

    public PhoneNumber getAdminContactNumber() {
        return adminContactNumber;
    }

    public void setAdminContactNumber(PhoneNumber adminContactNumber) {
        this.adminContactNumber = adminContactNumber;
    }

    @Override
    public String toString() {
        return "MyAppProperties{\n" +
                "mainScreenProperties=" + mainScreenProperties +
                ", adminContactNumber=" + adminContactNumber +
                "\n}";
    }
}