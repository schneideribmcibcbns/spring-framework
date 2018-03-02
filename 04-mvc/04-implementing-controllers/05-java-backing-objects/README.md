# Binding Request Parameters and Path Variables to Java Backing Objects

The default Spring Data binding mechanism allows us to bind the HTTP request details to the application specific objects.

The object must obey JavaBean convention.

Spring uses WebDataBinder which is a subclass of DataBinder.

WebDataBinder uses PropertyEditors to bind request parameter to handler method arguments. The PropertyEditor concept is part of the JavaBeans specification.

Check out [JavaBeans specs and API quick tutorial](https://www.logicbig.com/tutorials/core-java-tutorial/java-se-api/java-beans-specs.html) if you are not familiar with that. Also see [Spring Core PropertyEditors support](https://www.logicbig.com/tutorials/spring-framework/spring-core/property-editors.html).

## Binding Request Parameters

### Using @RequestParam

As we saw in the previous tutorials that one way to bind request parameters is to use @RequestParam annotation. In this example we are mapping /trades?buySell=buy&buyCurrency=EUR&sellCurrency=USD:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping
    public String handleTradeRequest (@RequestParam("buySell") String buySell,
                                      @RequestParam("buyCurrency") String buyCurrency,
                                      @RequestParam("sellCurrency") String sellCurrency,
                                      Model map) {
        String msg = String.format(
                        "trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                         buySell, buyCurrency, sellCurrency);
        map.addAttribute("msg", msg);
        return "my-page";
    }
}
```

### Binding Java Object

@RequestParam should be used if there are few parameters to bind with. But as number of request parameters increases our controller method becomes unmaintainable. There, we should create a Java class to map the request parameters to the class properties. The class should be standard JavaBean and property name should match the request param names.

Creating Object to capture request params:

```java
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

   .................
}
```

Using above object in the controller:

```java
@Controller
@RequestMapping("trades")
public class TradeController {
    @RequestMapping
    public String handleTradeRequest (Trade trade,
                                      Model map) {
        String msg = String.format(
                          "trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                           trade.getBuySell(), trade.getBuyCurrency(),
                           trade.getSellCurrency());
        map.addAttribute("msg", msg);
        return "my-page";
    }
```

Note we don't have to use the annotation @RequestParam with binding object.

We can also use our binding object in the JSP view without explicitly adding that into the Model object.

```jsp
<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
   <h3> Message : ${msg} <h3>
   <p>BuySell: ${trade.buySell}</p>
   <p>Buy Currency: ${trade.buyCurrency}</p>
   <p>Sell Currency: ${trade.sellCurrency}</p>
</body>
</html>
```

In fact the Model object is already populated with our binding object before the handler method is called by Spring. You can see what's inside Model by printing it in the handler.

## Binding Path Variables

### Using @PathVariables

Modifying our above example to use URI template variables:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping("{buySell}/{buyCurrency}/{sellCurrency}")
    public String handleTradeRequest3 (@PathVariable("buySell") String buySell,
                                       @PathVariable("buyCurrency") String buyCurrency,
                                       @PathVariable("sellCurrency") String sellCurrency,
                                       Model map) {
        String msg = String.format(
                       "trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                       buySell, buyCurrency, sellCurrency);
        map.addAttribute("msg", msg);
        return "my-page";
    }
}
```

The above handler will map to the request: /trades/buy/EUR/USD

### Using Java Object

We don't have to use @PathVariable annotation:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping("{buySell}/{buyCurrency}/{sellCurrency}")
    public String handleTradeRequest (Trade trade, Model map) {
        String msg = String.format(
                           "trade request. buySell: %s, buyCurrency: %s, sellCurrency: %s",
                           trade.getBuySell(), trade.getBuyCurrency(),
                           trade.getSellCurrency());

        map.addAttribute("msg", msg);
        return "my-page";
    }
}
```
