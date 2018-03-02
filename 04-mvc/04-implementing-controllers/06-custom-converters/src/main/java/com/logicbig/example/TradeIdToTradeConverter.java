package com.logicbig.example;


import org.springframework.core.convert.converter.Converter;

public class TradeIdToTradeConverter implements Converter<String, Trade> {

    private TradeService tradeService;

    public TradeIdToTradeConverter (TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public Trade convert (String id) {
        try {
            Long tradeId = Long.valueOf(id);
            return tradeService.getTradeById(tradeId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}