# Type safe properties binding with @ConfigurationProperties

Spring Boot's annotation @ConfigurationProperties is a type safe way to automatically bind external properties with Java Objects.

The target object fields' names should be compatible with the external property names. Spring Boot provides relaxed binding rules which can automatically parse external properties to Java fields/properties. These rules take care of things like hyphen and underscore etc. Check out the [rules](http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-relaxed-binding) here.

Let's understand how @ConfigurationProperties works with an example.

We are going to use both `application.properties` and `application.yml` properties together.

**src/main/resources/application.yml**

```yml
app:
  send-email-on-errors: true
  exit-on-errors: true
  order-screen-properties:
       title:      Order Placement
       item-label: Item
       qty-label:  Qunatity
  customer-screen-properties:
       title:       Customer Profile
       name-label:  Customer Name
       phone-label: Contact Number
```

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.main.logStartupInfo=false
app.refresh-rate=15
app.admin-emails[0]=jim@example.com
app.admin-emails[1]=gina@example.com
app.admin-emails[2]=tom@examle.com
```

**Binding properties with @ConfigurationProperties**

```java
@Component
@ConfigurationProperties("app")
public class MyAppProperties {
  private List<String> adminEmails;
  boolean sendEmailOnErrors;
  boolean exitOnErrors;
  private int refreshRate;
  private Map<String, String> orderScreenProperties;
  private Map<String, String> customerScreenProperties;
    .............
}
```

**The Main class**

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext context = SpringApplication.run(ExampleMain.class, args);
      MyAppProperties bean = context.getBean(MyAppProperties.class);
      System.out.println(bean);
  }
}
```

**Output**

```shell
2017-07-31 22:06:41.113  INFO 13048 --- [mpleMain.main()] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
MyAppProperties{
 adminEmails=[jim@example.com, gina@example.com, tom@examle.com],
 sendEmailOnErrors=true,
 exitOnErrors=true,
 refreshRate=15,
 orderScreenProperties={title=Order Placement, item-label=Item, qty-label=Qunatity},
 customerScreenProperties={title=Customer Profile, name-label=Customer Name, phone-label=Contact Number}
}
2017-07-31 22:06:41.223  INFO 13048 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```

**Note:**

* If we are registering our configuration class (`MyAppProperties` in above example) via @Bean method, instead of @Component, then @ConfigurationProperties annotation can be used on the @Bean method:

```java
@SpringBootApplication
public class ExampleMain {
    @Bean
    @ConfigurationProperties("app")
    MyAppProperties myAppProperties(){
        return new MyAppProperties();
    }
 .....
}
```

As we don't need to use any annotation on the target class (`MyAppProperties` in above example), this configuration style can be useful when we want to bind properties to third-party components that are outside of our control.

* We can also use `@EnableConfigurationProperties(MyAppProperties.class)` annotation along with `@SpringBootApplication` (the main configuration class). In this case we don't have to use @Component on our configuration class or register it via @Bean. The configuration class will automatically be registered as bean by Spring Boot.

```java
@SpringBootApplication
@EnableConfigurationProperties(MyAppProperties.class)
public class ExampleMain {
 ...
}
```

```java
@ConfigurationProperties("app")
public class MyAppProperties {
...
}
```