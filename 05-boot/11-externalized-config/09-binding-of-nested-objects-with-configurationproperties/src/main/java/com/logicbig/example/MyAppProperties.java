package com.logicbig.example;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class MyAppProperties {

    private boolean sendEmailOnErrors;
    private boolean exitOnErrors;
    private OrderScreenProperties orderScreenProperties;
    private CustomerScreenProperties customerScreenProperties;
    private MainScreenProperties mainScreenProperties;

    public OrderScreenProperties getOrderScreenProperties() {
        return orderScreenProperties;
    }

    public void setOrderScreenProperties(OrderScreenProperties orderScreenProperties) {
        this.orderScreenProperties = orderScreenProperties;
    }

    public CustomerScreenProperties getCustomerScreenProperties() {
        return customerScreenProperties;
    }

    public void setCustomerScreenProperties(CustomerScreenProperties customerScreenProperties) {
        this.customerScreenProperties = customerScreenProperties;
    }

    public boolean isSendEmailOnErrors() {
        return sendEmailOnErrors;
    }

    public void setSendEmailOnErrors(boolean sendEmailOnErrors) {
        this.sendEmailOnErrors = sendEmailOnErrors;
    }

    public boolean isExitOnErrors() {
        return exitOnErrors;
    }

    public void setExitOnErrors(boolean exitOnErrors) {
        this.exitOnErrors = exitOnErrors;
    }

    public MainScreenProperties getMainScreenProperties() {
        return mainScreenProperties;
    }

    public void setMainScreenProperties(MainScreenProperties mainScreenProperties) {
        this.mainScreenProperties = mainScreenProperties;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                ",\n sendEmailOnErrors=" + sendEmailOnErrors +
                ",\n exitOnErrors=" + exitOnErrors +
                ",\n orderScreenProperties=" + orderScreenProperties +
                ",\n customerScreenProperties=" + customerScreenProperties +
                ",\n mainScreenProperties=" + mainScreenProperties +
                "\n}";
    }
}