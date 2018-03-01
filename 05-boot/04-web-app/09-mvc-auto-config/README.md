# Understanding Spring Boot Web MVC Auto Configuration

WebMvcAutoConfiguration is responsible for Auto-configuration for Web MVC. Following is its snippet (spring-boot-1.5.9.RELEASE):

```java
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurerAdapter.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class,	ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
 ...
}
```

As seen in above snippet, the following conditions must be met for this auto configuration to work:

1. Servlet, DispatcherServlet and WebMvcConfigurer classes must be present on the classpath. If the dependency of spring-boot-starter-web has been included in the project then all of these classes will be present.

2. The application context being used must be web application context. When we start spring boot application via SpringApplication class, AnnotationConfigEmbeddedWebApplicationContext is initialized as ApplicationContext only if ConfigurableWebApplicationContext is on the classpath (included when used spring-boot-starter-web), otherwise AnnotationConfigApplicationContext is initialized.

3. WebMvcConfigurationSupport must not already be registered as bean. In a typical web MVC application, this class is registered when we use @EnableWebMvc.

## Adding additional Spring Web MVC Components

If we need to add additional MVC configuration (interceptors, formatters, view controllers etc.) we can add our own @Configuration class of type WebMvcConfigurerAdapter ([example here](../10-custom-formatter/README.md).

## Where to find Boot MVC Configurations?

It's good to know where Spring Boot does MVC related beans registration and configuration so that we can investigate and find answers if they are not available in the documentations. Following are two important classes:

WebMvcAutoConfigurationAdapter is a nested configuration class in WebMvcAutoConfiguration which extends WebMvcConfigurerAdapter and has Boot specific necessary beans registration for Web MVC.

The other nested configuration class we should know is EnableWebMvcConfiguration. This class extends DelegatingWebMvcConfiguration which is capable of discovering more WebMvcConfigurer (needed for application side configuration as stated in the last section).