# Custom Type Conversion with @ConfigurationProperties

As we saw in the last tutorial, Spring automatically converts the external properties to the target type when it binds to the @ConfigurationProperties beans. If we need custom type conversion, we can provide a custom Converter. We can register our custom converter as a bean by using Spring boot `@ConfigurationPropertiesBinding` annotation. Let's see an example how to do that.

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.main.logStartupInfo=false
app.exit-on-errors=true
app.trade-start-date=03-25-2016
```

From above properties, we will target `app.trade-start-date` to `java.datetime.LocalDate`:

```java
@Component
@ConfigurationProperties("app")
public class MyAppProperties {
  private boolean exitOnErrors;
  private LocalDate tradeStartDate;
    .............
}
```

**The Custom Converter**

We are going to provide a custom converter which will do our desire conversion. This converter should be registered as a Spring Bean and should also be annotated with `@ConfigurationPropertiesBinding` which is a Qualifier annotation. The `ConfigurationPropertiesBindingPostProcessor` is responsible to bind all external properties to the beans annotated with `@ConfigurationProperties`. This BeanPostProcessor also detects custom converters (besides the default ones) annotated with `@ConfigurationPropertiesBinding` for type conversion.

```java
@Component
@ConfigurationPropertiesBinding
public class LocalDateConverter implements Converter<String, LocalDate> {
  @Override
  public LocalDate convert(String source) {
      if(source==null){
          return null;
      }
      return LocalDate.parse(source, DateTimeFormatter.ofPattern("MM-dd-yyyy"));
  }
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
2017-08-09 10:05:28.089  INFO 9316 --- [mpleMain.main()] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
MyAppProperties{exitOnErrors=false, tradeStartDate=2016-03-25}
2017-08-09 10:05:28.217  INFO 9316 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```

**Notes**

Other than using a Converter for our custom type conversion, we can use other options as well. From Spring boot reference doc:

If you need custom type conversion you can provide a ConversionService bean (with bean id conversionService) or custom property editors (via a CustomEditorConfigurer bean) or custom Converters (with bean definitions annotated as @ConfigurationPropertiesBinding).