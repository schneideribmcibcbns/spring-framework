# HttpMessageConverter Registration

This example demonstrates how to register a custom HttpMessageConverter.

## A custom converter

This is just an empty extension of StringHttpMessageConverter for keeping things simple.

```
public class TheCustomConverter extends StringHttpMessageConverter {
}
```

## Printing list of registered converters

```
@Component
public class RefreshListener {
  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
      handlerAdapter.getMessageConverters().stream()
                    .map(c -> c.toString())
                    .forEach(System.out::println);
  }
}
```

## Extending WebMvcConfigurerAdapter

### Overriding extendMessageConverters()

```
@ComponentScan
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

   @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TheCustomConverter());
    }
 ......
}
```

### Overriding configureMessageConverters()

Following configuration will replace all default converters:

```
@ComponentScan
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

   @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new TheCustomConverter());
    }
 ......
}
```