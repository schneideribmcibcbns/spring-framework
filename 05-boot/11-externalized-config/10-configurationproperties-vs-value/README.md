# @ConfigurationProperties vs @Value in terms of Relaxed Binding and Implicit Type Conversion

We saw in last two examples how relaxed binding rules are applied to @ConfigurationProperties.

Regardless of whether we are using @ConfigurationProperties or @Value annotation, the implicit type conversion (via [property editors](../../../01-core/07-data-binding-validation-conversion-formatting/02-property-editors/README.md) or [DataBinder](../../../01-core/07-data-binding-validation-conversion-formatting/03-core-data-binding/README.md) or [Conversion service](../../../01-core/07-data-binding-validation-conversion-formatting/06-conversion-service/README.md)) is applied in both cases. That means all text values from external properties are implicitly converted to target type given that Spring conversion mechanism supports that type. In case of @ConfigurationProperties, relaxed binding can additionally be applied during this conversion. That means the properties values don't always have to be case sensitive (e.g. an Enum element value). That is not the case with @Value where the property value has to be exactly of same letter-case. Let's understand that with an example.

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off 
spring.main.logStartupInfo=false
app.trade-currency=USD
app.refresh-time-unit=seconds
app.refresh-rate=5
```

**Properties Class**

```java
@Component
@ConfigurationProperties("app")
public class MyAppProperties {
  private int refreshRate;
  private TimeUnit refreshTimeUnit;
  private Currency tradeCurrency;
    .............
}
```

**The Main class**

```java
@SpringBootApplication
public class ConfigPropertyExampleMain {

  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext context = SpringApplication.run(ConfigPropertyExampleMain.class, args);
      MyAppProperties bean = context.getBean(MyAppProperties.class);
      System.out.println(bean);
  }
}
```

**Output**

```shell
2017-08-05 00:02:16.947  INFO 9092 --- [mpleMain.main()] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
MyAppProperties{
refreshRate=5,
 refreshTimeUnit=SECONDS,
 tradeCurrency=USD
}
2017-08-05 00:02:17.086  INFO 9092 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```

As seen above, `app.refresh-time-unit=seconds` property converted to `TimeUnit.SECONDS`, even though we used lower case for 'seconds'. In case of `app.trade-currency=USD` however, we cannot use lower case or mixed case, that's because this conversion is not same as targeting a 'type' but it is rather using the property value as parameter to `java.util.Currency.getInstance(source)` method.

Let's try @Value for the same external properties:

```java
public class MyConfigBean {
  @Value("${app.refresh-rate}")
  private int refreshRate;
  @Value("${app.refresh-time-unit}")
  private TimeUnit refreshTimeUnit;
  @Value("${app.trade-currency}")
  private Currency tradeCurrency;
    .............
}
```

```java
@SpringBootApplication
public class ValueExampleMain {
  @Bean
  public MyConfigBean myBean() {
      return new MyConfigBean();
  }

  public static void main(String[] args) {
      ConfigurableApplicationContext context = SpringApplication.run(ValueExampleMain.class, args);
      MyConfigBean bean = context.getBean(MyConfigBean.class);
      System.out.println(bean);
  }
}
```

**Output**

```log4j
org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'myBean': Unsatisfied dependency expressed through field 'refreshTimeUnit'; nested exception is org.springframework.beans.ConversionNotSupportedException: Failed to convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:588) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:88) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:366) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1264) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:553) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:483) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:761) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:867) ~[spring-context-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:543) ~[spring-context-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:693) [spring-boot-1.5.6.RELEASE.jar:1.5.6.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:360) [spring-boot-1.5.6.RELEASE.jar:1.5.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:303) [spring-boot-1.5.6.RELEASE.jar:1.5.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118) [spring-boot-1.5.6.RELEASE.jar:1.5.6.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107) [spring-boot-1.5.6.RELEASE.jar:1.5.6.RELEASE]
	at com.logicbig.example.ValueExampleMain.main(ValueExampleMain.java:16) [classes/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_111]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_111]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_111]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_111]
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:294) [exec-maven-plugin-1.5.0.jar:na]
	at java.lang.Thread.run(Thread.java:745) [na:1.8.0_111]
Caused by: org.springframework.beans.ConversionNotSupportedException: Failed to convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.TypeConverterSupport.doConvert(TypeConverterSupport.java:74) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.TypeConverterSupport.convertIfNecessary(TypeConverterSupport.java:54) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1092) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1066) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:585) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	... 24 common frames omitted
Caused by: java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:306) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:125) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	at org.springframework.beans.TypeConverterSupport.doConvert(TypeConverterSupport.java:61) ~[spring-beans-4.3.10.RELEASE.jar:4.3.10.RELEASE]
	... 28 common frames omitted

[WARNING]
java.lang.reflect.InvocationTargetException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.codehaus.mojo.exec.ExecJavaMojo$1.run(ExecJavaMojo.java:294)
	at java.lang.Thread.run(Thread.java:745)
Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'myBean': Unsatisfied dependency expressed through field 'refreshTimeUnit'; nested exception is org.springframework.beans.ConversionNotSupportedException: Failed to convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:588)
	at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:88)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:366)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1264)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:553)
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:483)
	at org.springframework.beans.factory.support.AbstractBeanFactory$1.getObject(AbstractBeanFactory.java:306)
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:230)
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:302)
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:197)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:761)
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:867)
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:543)
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:693)
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:360)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1118)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1107)
	at com.logicbig.example.ValueExampleMain.main(ValueExampleMain.java:16)
	... 6 more
Caused by: org.springframework.beans.ConversionNotSupportedException: Failed to convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit'; nested exception is java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.TypeConverterSupport.doConvert(TypeConverterSupport.java:74)
	at org.springframework.beans.TypeConverterSupport.convertIfNecessary(TypeConverterSupport.java:54)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1092)
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1066)
	at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredFieldElement.inject(AutowiredAnnotationBeanPostProcessor.java:585)
	... 24 more
Caused by: java.lang.IllegalStateException: Cannot convert value of type 'java.lang.String' to required type 'java.util.concurrent.TimeUnit': no matching editors or conversion strategy found
	at org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:306)
	at org.springframework.beans.TypeConverterDelegate.convertIfNecessary(TypeConverterDelegate.java:125)
	at org.springframework.beans.TypeConverterSupport.doConvert(TypeConverterSupport.java:61)
	... 28 more
```

Above output shows that 'seconds' could not be converted to TimeUnit enum. To fix above exception, we need to replace 'seconds' with 'SECONDS'.