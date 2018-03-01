# Managing HttpMessageConverters

Http message converters are used to marshall and unmarshall Java Objects to and from JSON, XML, etc – over HTTP. There are many http message converters:

* ByteArrayHttpMessageConverter – converts byte arrays
* StringHttpMessageConverter – converts Strings
* ResourceHttpMessageConverter – converts org.springframework.core.io.Resource for any type of octet stream
* SourceHttpMessageConverter – converts javax.xml.transform.Source
* FormHttpMessageConverter – converts form data to/from a MultiValueMap<String, String>.
* Jaxb2RootElementHttpMessageConverter – converts Java objects to/from XML 
* MappingJackson2HttpMessageConverter – converts JSON 
* MappingJacksonHttpMessageConverter – converts JSON 
* AtomFeedHttpMessageConverter – converts Atom feeds 
* RssChannelHttpMessageConverter – converts RSS feeds 

HttpMessageConverters provides a convenient way to manage HttpMessageConverters to a web application. It can be registered as a bean. The following public constructors of this class can be used:

* HttpMessageConverters(HttpMessageConverter<?>... additionalConverter)
* HttpMessageConverters(Collection<HttpMessageConverter<?>> additionalConverters)
* HttpMessageConverters(boolean addDefaultConverters, Collection<HttpMessageConverter<?>> converters)

## Adding additional converters

```java
@SpringBootApplication
public class AppMain {
   @Bean
   public HttpMessageConverters additionalConverters() {
       return new HttpMessageConverters(new TheCustomConverter());
   }
}
```

```java
@Component
public class RefreshListener {
  @Autowired
  private RequestMappingHandlerAdapter handlerAdapter;

  @EventListener
  public void handleContextRefresh(ContextRefreshedEvent event) {
      handlerAdapter.getMessageConverters()
                    .stream()
                    .forEach(System.out::println);
  }
}
```

**Output**

Running the main class:

```shell
org.springframework.http.converter.ByteArrayHttpMessageConverter@41838c01
com.logicbig.example.TheCustomConverter@7ce24b48
org.springframework.http.converter.StringHttpMessageConverter@69455568
org.springframework.http.converter.ResourceHttpMessageConverter@4997399d
org.springframework.http.converter.xml.SourceHttpMessageConverter@1429472a
org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter@7affe6ec
org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@39d202ce
org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter@49f98fb8
```

## Replacing default converters

```java
@SpringBootApplication
public class AppMain {
  @Bean
  public HttpMessageConverters converters() {
      return new HttpMessageConverters(
              false, Arrays.asList(new TheCustomConverter()));
  }
}
```

**Output**

```shell
com.logicbig.example.TheCustomConverter@305e1d50
```

## Registering HttpMessageConverter as Bean

An HttpMessageConverter can also be registered as a bean directly:

```java
@SpringBootApplication
public class AppMain {
   @Bean
   public HttpMessageConverter<?> messageConverter(){
       return new TheCustomConverter();
   }
}
```

It will produce the same output as the last one.
