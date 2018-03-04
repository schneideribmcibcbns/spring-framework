# Type safe binding of nested objects with @ConfigurationProperties

This example is an improvement of the [last example](../08-configurationproperties/README.md).

Here we will see how to bind external properties to nested Java objects.

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
app.main-screen-properties.refresh-rate=15
app.main-screen-properties.width=600
app.main-screen-properties.height=400
```

**Using @ConfigurationProperties**

Comparing with the last example, we are using type safe objects instead of maps for nested properties:

```java
@Component
@ConfigurationProperties("app")
public class MyAppProperties {
  private boolean sendEmailOnErrors;
  private boolean exitOnErrors;
  private OrderScreenProperties orderScreenProperties;
  private CustomerScreenProperties customerScreenProperties;
  private MainScreenProperties mainScreenProperties;
    .............
}
```

```java
public class OrderScreenProperties {
  private String title;
  private String itemLabel;
  private String qtyLabel;
    .............
}
```

```java
public class CustomerScreenProperties {
  private String title;
  private String nameLabel;
  private String phoneLabel;
    .............
}
```

```java
public class MainScreenProperties {
  private int refreshRate;
  private int width;
  private int height;
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
2017-07-31 15:34:19.856  INFO 16684 --- [mpleMain.main()] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
MyAppProperties{,
 sendEmailOnErrors=true,
 exitOnErrors=true,
 orderScreenProperties=OrderScreenProperties{title='Order Placement', itemLabel='Item', qtyLabel='Qunatity'},
 customerScreenProperties=CustomerScreenProperties{title='Customer Profile', nameLabel='Customer Name', phoneLabel='Contact Number'},
 mainScreenProperties=MainScreenProperties{refreshRate=15, width=600, height=400}
}
2017-07-31 15:34:19.978  INFO 16684 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```