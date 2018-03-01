# Importing Configurations with @EnableWebMvc

Adding this annotation to a @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport which is the main class providing the configuration behind the MVC Java config.

```java
@EnableWebMvc
@Configuration
public class MyWebConfig {
 .....
}
```

If you don't use this annotation you might not initially notice any difference but things like content-type and accept header, generally content negotiation won't work.

According to this jira

 This looks like one of the differences between our old AnnotationMethodHandlerAdapter implementation and our 3.1+ RequestMethodHandlerAdapter. Indeed, for all practical purposes, you should always use @EnableWebMvc which leads to the use of our modern-day infrastructure. For backwards compatibility with 2.5 and 3.0 based applications, DispatcherServlet still uses the old variant if no explicit configuration is given. Note that this will be streamlined as of 5.0, with the old infrastructure getting removed then.

If you are using XML based configuration then use <mvc:annotation-driven/> as an alternative to @EnableWebMvc

@EnableWebMvc and <mvc:annotation-driven /> have the same purpose, mixing them doesn't work in some cases.

Following is the @EnableWebMvc snippet:

```java
package org.springframework.web.servlet.config.annotation;
 ...
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(DelegatingWebMvcConfiguration.class)
public @interface EnableWebMvc {
}
```

As seen above EnableWebMvc imports DelegatingWebMvcConfiguration which is a subclass of WebMvcConfigurationSupport. See the list of configuration performed by this support class [here](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/WebMvcConfigurationSupport.html).

## How to Customize Configurations?

To customize the configuration imported by @EnableWebMvc, we should extend the class WebMvcConfigurerAdapter and override the methods we want to do related customization with. Our extended WebMvcConfigurerAdapter methods are called back from WebMvcConfigurationSupport during configuration stage.

If overriding WebMvcConfigurer does not work for us and we want to do some advance configuration then we should not use @EnableWebMvc annotation. In that case we should extend our @Configuration class directly from WebMvcConfigurationSupport or DelegatingWebMvcConfiguration and selectively override the methods including factory methods annotated with @Bean.

See also [@EnableWebMvc](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/config/annotation/EnableWebMvc.html) for quick examples.

## How WebMvcConfigurerAdapter is called back?

Using @EnableWebMvc in our configuration class, will import configuration from DelegatingWebMvcConfiguration. Let's see the @EnableWebMvc definition snippet:

```java
....
@Import(DelegatingWebMvcConfiguration.class)
  public @interface EnableWebMvc {
}
```

DelegatingWebMvcConfiguration is a subclass of WebMvcConfigurationSupport (which is responsible for all Spring configuration from new infrastructure). The configuration class DelegatingWebMvcConfiguration has following dependency injection:

```java
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
   .....
  @Autowired(required = false)
  public void setConfigurers(List<WebMvcConfigurer> configurers) {
   	if (!CollectionUtils.isEmpty(configurers)) {
		this.configurers.addWebMvcConfigurers(configurers);
	}
  }
}
```

That means if we extend our @Configuration class with WebMvcConfigurerAdapter (an adapter implementation of WebMvcConfigurer) then it will also get injected above.

During configuration time, various factory methods annotated with @Bean in WebMvcConfigurationSupport cause a callback to our WebMvcConfigurer implementation. For example:

```java
public class WebMvcConfigurationSupport implements ... {
    @Bean
    public HandlerMapping viewControllerHandlerMapping() {
        ViewControllerRegistry registry = new ViewControllerRegistry();
        registry.setApplicationContext(this.applicationContext);
        addViewControllers(registry);
             ......
        return handlerMapping;
    }
    ....
    protected void addViewControllers(ViewControllerRegistry registry) {
    }
    ....
}
```

addViewControllers() method is overridden by DelegatingWebMvcConfiguration which finally calls back our WebMvcConfigurerAdapter methods:

```
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
    ...
    @Override
	protected void addViewControllers(ViewControllerRegistry registry) {
	   this.configurers.addViewControllers(registry);
	}
    ...
}
```

**configurers** is the same injected list, as we saw in above snippet. Our @Configuration class will look like this:

```java
@EnableWebMvc
@Configuration
public class MyWebConfig extends WebMvcConfigurerAdapter {
  .....
   @Override
    public void addViewControllers (ViewControllerRegistry registry) {
        //our customization
    }
  ...
 }
 ```