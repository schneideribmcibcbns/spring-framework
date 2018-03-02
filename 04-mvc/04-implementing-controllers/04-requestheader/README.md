# Binding HTTP Request Header Attributes with @RequestHeader

The @RequestHeader annotation is used to bind HTTP request header attributes values to controller method parameters.

## @RequestHeader using 'value' element

@RequestHeader element 'value' is used to specify HTTP request header name:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping
    public String handleAllTradesRequests (@RequestHeader("User-Agent") String userAgent,
                                           Model model) {
        model.addAttribute("msg", "all trades requests, User-Agent header  : "
                                                                   + userAgent);
        return "my-page";
    }
}
```

## @RequestHeader without 'value' element

Just like @PathVariable and @RequestParam, 'value' element of @RequestHeader annotation can be skipped if type variable name is same as header name. Also the code should be compiled with debugging information (variable names must be included in the debugging information).

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping
    public String handleRequestByFromHeader (@RequestHeader String From, Model model) {
        LOGGER.info("trade request by From header  : " + From);
        model.addAttribute("msg", "trade request by From header  : " + From);
        return "my-page";
    }
}
```

Sometimes Header names are not java valid variable names e.g. User-Agent, in that case we cannot use this method of header binding.

## Using multiple @RequestHeader annotations

A method can have any number of @RequestHeaders annotations:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    public String handleRequestByTwoHeaders(@RequestHeader("User-Agent") String userAgent,
                            @RequestHeader("Accept-Language") String acceptLanguage,
                                             Model map) {
        map.addAttribute("msg", "Trade request by User-Agent and Accept headers : " +
                            userAgent + ", " + acceptLanguage);
        return "my-page";
  }
}
```

## Using Map with @RequestHeader for all headers

If the method parameter is Map<String, String> or MultiValueMap<String, String> then the map is populated with all request header names and values.

```java
 @Controller
 @RequestMapping("trades")
 public class TradeController {
    @RequestMapping
    public String handleRequestWithAllHeaders (@RequestHeader Map<String, String> header,
                                               Model model) {
        model.addAttribute("msg", "Trade request with all headers " + headers);
        return "my-page";
    }
}
```

## Using HttpHeaders with @RequestHeader for all headers

We can also use method parameter of type HttpHeaders along with the annotation @RequestHeader to get all headers. HttpHeaders is Spring specific type which implements  MultiValueMap<String,String> and defines all convenience methods like getContentType(), getCacheControl() etc.

 @Controller
 @RequestMapping("trades")
 public class TradeController {
    @RequestMapping
    public String handleRequestWithAllHeaders (@RequestHeader HttpHeaders httpHeaders,
                                               Model model) {
        model.addAttribute("msg", "Trade request with all headers " + httpHeaders);
        return "my-page";
    }
}


## Auto type conversion

If target method parameter is not String, automatic type conversion can happen. All simple types such as int, long, Date, etc. are supported by default.

```java
@Controller
@RequestMapping("trades")
public class TradeController{

   @RequestMapping(value = "{tradeId}")
    public String handleRequestById (@PathVariable("tradeId") String tradeId,
                                     @RequestHeader("If-Modified-Since") Date date,
                                     Model model) {

      model.addAttribute("msg", "Trade request by trade id and If-Modified-Since : " +
                            tradeId + ", " + date);
      return "my-page";
    }
}
```

## The 'required' element of @RequestHeader

This element defines whether the header is required. The default is true. That means the status code 400 will be returned if the header is missing in the request. We can switch this to false if we prefer a null value if the header is not present in the request. Following handler will still map even though header 'Accept' is not present in the request:

```java
@Controller
@RequestMapping("trades")
public class TradeController {

    @RequestMapping(value = "exchangeRates")
    public String handleExchangeRatesRequest (
                                        @RequestHeader(value = "Accept", required = false)
                                        String acceptHeader,
                                        Model model) {

        model.addAttribute("msg", "exchange rates request.  AcceptHeader: " +
                                                                    acceptHeader);
        return "my-page";
    }
}
```

In above example we can alternatively use @RequestMapping(... produces= "application/xml") instead of mapping via @RequestHeader(value = "Accept"). In that case, however, the handler method will only match if header has Accept="application/xml". Whereas, above example will map Accept header doesn't matter what value Accept has been assigned to.

## The 'defaultValue' element of @RequestHeader

The defaultValue element is used as a fallback value when the request header specified by 'value' element is not provided or has an empty value. Supplying a default value implicitly sets required to false.

In our last example we can specify a default value for 'Accept' header. In that case we don't have to specify 'required=false'. Now if the value for this header is present or not, the handler method will still be mapped.

@RequestHeader(value = "Accept", defaultValue="application/json") String acceptHeader


## Same base URI methods with different request headers are ambiguous

Defining different request headers does not define different URI paths. Following handler methods will cause runtime exception, complaining about ambiguous mapping.

```java 
@Controller
@RequestMapping("/trades")
public class TradeController {

    @RequestMapping
    public String handleAllTradesRequests (@RequestHeader("User-Agent") String userAgent,
                                           Model model) {
        model.addAttribute("msg", "all trades requests, User-Agent header  : "
                                                                   + userAgent);
        return "my-page";
    }

    @RequestMapping
    public String handleRequestByFromHeader (@RequestHeader("From") String from,
                                             Model model) {
        model.addAttribute("msg", "trade request by From header  : " + from);
        return "my-page";
    }

} 
```

output:

```shell
Caused by: java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'myMvcController' method
public java.lang.String com.logicbig.example.TradeController.handleRequestByFromHeader(java.lang.String,org.springframework.ui.Model)
to {[/trades]}: There is already 'myMvcController' bean method
public java.lang.String com.logicbig.example.TradeController.handleAllTradesRequests(java.lang.String,org.springframework.ui.Model) mapped.
```

## Avoid ambiguity by using @RequestMapping(headers = ....)

We can fix the ambiguity similar to @RequestParam where we used 'params' . In case of @RequestHeader we can define related headers in @RequestMapping annotation.

```java
@Controller
@RequestMapping("trades")
public class TradesController {

   @RequestMapping(headers = "User-Agent")
    public String handleAllTradesRequests (@RequestHeader("User-Agent") String userAgent,
                                           Model model) {
        model.addAttribute("msg", "all trades requests, User-Agent header  : "
                                                                    + userAgent);
        return "my-page";
    }

    @RequestMapping(headers = "From")
    public String handleRequestByFromHeader (@RequestHeader("From") String from,
                                             Model model) {
        model.addAttribute("msg", "trade request by From header  : " + from);
        return "my-page";
    }
}
```