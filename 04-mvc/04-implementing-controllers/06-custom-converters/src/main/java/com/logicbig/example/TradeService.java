package com.logicbig.example;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TradeService {
    private HashMap<Long, Trade> trades = new HashMap<>();

    @PostConstruct
    private void postConstruct () {
        //just populating with some dummy data
        //in real application will get the data from a database
        List<Currency> ccy = new ArrayList(Currency.getAvailableCurrencies());
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 1; i <= 10; i++) {
            Trade trade = new Trade();
            trade.setTradeId(i);
            trade.setBuySell(Math.random() > 0.5 ? "Buy" : "Sell");
            trade.setBuyCurrency(ccy.get(rnd.nextInt(0, ccy.size()))
                                    .getCurrencyCode());
            trade.setSellCurrency(ccy.get(rnd.nextInt(0, ccy.size()))
                                     .getCurrencyCode());
            trades.put((long) i, trade);
        }
    }

    public Trade getTradeById (long id) {
        return trades.get(id);

    }

    public List<Trade> getAllTrades () {
        return new ArrayList<>(trades.values());
    }
}