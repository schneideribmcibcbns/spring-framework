package com.logicbig.example;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@EnableWebMvc
@Configuration
@Import(MyViewConfig.class)
public class TradeConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TradeController tradeParamRequestController() {
        return new TradeController();
    }

    @Bean
    public TradeService tradeService(){
        return new TradeService();
    }

    
    @Override
    public void addFormatters (FormatterRegistry registry) {
        registry.addConverter(new TradeIdToTradeConverter(tradeService()));
    }
    
}