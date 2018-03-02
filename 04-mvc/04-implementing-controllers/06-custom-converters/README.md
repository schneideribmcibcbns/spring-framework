# Binding Java Backing Objects with Custom Converters

Instead of using PropertyEditor, we can use Spring conversion service for strings to type conversion. Please check out our [Spring core Conversion Service tutorial](https://www.logicbig.com/tutorials/spring-framework/spring-core/conversion-service.html) to get familiar with conversion service.

Spring MVC uses a single instance of ConversionService interface per container. This interface is the main interface Of the conversion service API.

In this example we are going to register a custom converter to map URI template variable to a Java object. This is an alternative to Spring implicit mapping of the path variable and request parameters to object properties, but with additional benefit; that is we can retrieve binding object from data layer or we can enrich the object on the fly.

We are going to create a custom converter to convert tradeId to Trade object. The Trade object is retrieved from data service based on trade id extracted from URI template.

## Creating the backing object

```java
public class Trade {

    private long tradeId;
    private String buySell;
    private String buyCurrency;
    private String sellCurrency;

    //getters and setters
}
```

## Creating the Converter

```java
import org.springframework.core.convert.converter.Converter;

public class TradeIdToTradeConverter implements Converter<String, Trade> {

    private TradeService tradeService;

    public TradeIdToTradeConverter(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @Override
    public Trade convert(String id) {
        try {
            Long tradeId = Long.valueOf(id);
            return tradeService.getTradeById(tradeId);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
```

## Registering the Converter

```java
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
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new TradeIdToTradeConverter(tradeService()));
    }
}
```

### What is WebMvcConfigurerAdapter?

WebMvcConfigurerAdapter implements WebMvcConfigurer which defines callback methods to customize the Java-based configuration for Spring MVC enabled via @EnableWebMvc.

In above example, we are overriding the method addFormatters(FormatterRegistry registry) to add our custom converter. Generally this method is used to add Converters and Formatters in addition to the ones registered by default. Check out [this tutorial](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-enablewebmvc-annotation.html#how-to-customize-configurations?) too.

## Creating the Controller

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping("/{trade}")
    public String handleTradeRequest(Trade trade, Model model) {
        if (trade.getTradeId() == 0) {
            model.addAttribute("msg", "No trade found");
            return "no-trade-page";
        }
        return "trade-page";
    }
}
```

Important: The name of the template variable has to be same as backing object parameter name of the handler method, otherwise Spring will map the template variable to object properties directly and our custom converter won't be used.


## trade-page.jsp

```jsp
<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
   <h3> Showing Trade with Id ${trade.tradeId} <h3>
   <p>BuySell: ${trade.buySell}</p>
   <p>Buy Currency: ${trade.buyCurrency}</p>
   <p>Sell Currency: ${trade.sellCurrency}</p>
</body>
</html>
```

Note that, we are using trade object reference in our jsp page, that's because the Trade object is implicitly populated in Model object by Spring; that happens even before our controller's handler method gets called.

