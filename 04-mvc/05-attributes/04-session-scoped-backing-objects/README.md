# Using Session Scoped backing Objects

In the last tutorial we saw how to use @SessionAttributes along with @ModelAttribute to store session objects.

In this tutorial, we are going to see a cleaner alternative.

A backing object can be autowired/injected to a Spring MVC controller with predefined scopes. These scopes are 'request' and 'session' scopes.

To understand how to use session scope object, we are going to modify our last example.

To avoid narrower scope bean DI problem as we saw when [injecting Prototype bean into a Singleton Bean](https://www.logicbig.com/tutorials/spring-framework/spring-core/injecting-singleton-with-prototype-bean.html), we will preferably use JSR 330 Provider approach. Here's a list of [other solutions](https://www.logicbig.com/tutorials/spring-framework/spring-core/injecting-singleton-with-prototype-bean.html#narrow-scoped-bean-sol).

Let's see some code. We are going to take following two steps:

Register 'Visitor' object as a bean in our @Configuration class. We are going to specify the scope as 'session'

```java
@EnableWebMvc
@Configuration
@Import(MyViewConfig.class)
public class MyWebConfig {

    @Bean
    public TradeController tradeController () {
        return new TradeController();
    }

    @Bean
    @Scope(WebApplicationContext.SCOPE_SESSION)
    public Visitor visitor(HttpServletRequest request){
         return new Visitor(request.getRemoteAddr());
    }
}
```

Inject our session bean wrapped in Provider interface:

```java
@Controller
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private Provider<Visitor> visitorProvider;

    @RequestMapping("/**")
    public String handleRequestById (Model model, HttpServletRequest request) {
        model.addAttribute("msg", "trades request, serving page "
                                                     + request.getRequestURI());
        visitorProvider.get()
                       .addPageVisited(request.getRequestURI());

        return "traders-page";
    }
}
```

## How it works?

The factory method of @Configuration class, MyWebConfig#visitor, is called at the beginning of each new HTTP session.

The return object from the factory method is stored in HTTPSession and is kept there till the session ends.

visitorProvider.get() returns the same instance for the same session.
