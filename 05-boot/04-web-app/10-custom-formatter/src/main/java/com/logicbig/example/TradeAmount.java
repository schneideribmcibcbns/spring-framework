package com.logicbig.example;

import java.math.BigDecimal;
import java.util.Currency;

public class TradeAmount {
    private Currency currency;
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "TradeAmount{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}