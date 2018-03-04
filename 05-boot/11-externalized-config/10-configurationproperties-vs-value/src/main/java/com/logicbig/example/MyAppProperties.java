package com.logicbig.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.concurrent.TimeUnit;

@Component
@ConfigurationProperties("app")
public class MyAppProperties {

    private int refreshRate;
    private TimeUnit refreshTimeUnit;
    private Currency tradeCurrency;

    public int getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public TimeUnit getRefreshTimeUnit() {
        return refreshTimeUnit;
    }

    public void setRefreshTimeUnit(TimeUnit refreshTimeUnit) {
        this.refreshTimeUnit = refreshTimeUnit;
    }

    public Currency getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(Currency tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    @Override
    public String toString() {
        return "MyAppProperties{" +
                "\nrefreshRate=" + refreshRate +
                ",\n refreshTimeUnit=" + refreshTimeUnit +
                ",\n tradeCurrency=" + tradeCurrency +
                "\n}";
    }
}