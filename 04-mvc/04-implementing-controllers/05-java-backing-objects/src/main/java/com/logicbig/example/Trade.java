package com.logicbig.example;

public class Trade {

    private String buySell;
    private String buyCurrency;
    private String sellCurrency;

    public String getBuySell () {
        return buySell;
    }

    public void setBuySell (String buySell) {
        this.buySell = buySell;
    }

    public String getBuyCurrency () {
        return buyCurrency;
    }

    public void setBuyCurrency (String buyCurrency) {
        this.buyCurrency = buyCurrency;
    }

    public String getSellCurrency () {
        return sellCurrency;
    }

    public void setSellCurrency (String sellCurrency) {
        this.sellCurrency = sellCurrency;
    }
}