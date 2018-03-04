package com.logicbig.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConfigurationProperties("app")
public class MyAppProperties {
    private boolean exitOnErrors;
    private LocalDate tradeStartDate;

    public boolean isExitOnErrors() {
        return exitOnErrors;
    }

    public void setExitOnErrors(boolean exitOnErrors) {
        this.exitOnErrors = exitOnErrors;
    }

    public LocalDate getTradeStartDate() {
        return tradeStartDate;
    }

    public void setTradeStartDate(LocalDate tradeStartDate) {
        this.tradeStartDate = tradeStartDate;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                "exitOnErrors=" + exitOnErrors +
                ", tradeStartDate=" + tradeStartDate +
                '}';
    }
}