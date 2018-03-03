# Logging in Log4j2

## Log4j2 dependency

To use Log4j2 (an upgrade of Log4j), we need to add following dependencies:

```xml
<dependency>
   <groupId>org.apache.logging.log4j</groupId>
   <artifactId>log4j-core</artifactId>
   <version>2.8.2</version>
</dependency>
<dependency>
   <groupId>org.apache.logging.log4j</groupId>
   <artifactId>log4j-jcl</artifactId>
   <version>2.8.2</version>
</dependency>
```

## log4j2.properties

**src/main/resources/log4j2.properties**

```shell
status = error
name = PropertiesConfig
filters = threshold

filter.threshold.type = ThresholdFilter
filter.threshold.level = info

appenders = console

appender.console.type = Console
appender.console.name = STDOUT
appender.console.layout.type = PatternLayout
appender.console.layout.pattern = %d{yy-MM-dd HH:mm:ss:SSS} %-5p %c{1}:%L - %m%n

rootLogger.level = info
rootLogger.appenderRefs = stdout
rootLogger.appenderRef.stdout.ref = STDOUT
```

Configuration can be done in XML, JSON, YAML, or properties format. Check out different configuration options [here](https://logging.apache.org/log4j/2.0/manual/configuration.html).

## Example Bean

```java
public class MyBean {
  private static Log log = LogFactory.getLog(MyBean.class);

  public void doSomething() {
      log.info("doing something");
  }
}
```

## Main class

```java
@Configuration
public class ExampleMain {

  @Bean
  public MyBean myBean() {
      return new MyBean();
  }

  public static void main(String[] args) {
      ConfigurableApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain.class);

      MyBean bean = context.getBean(MyBean.class);
      bean.doSomething();
  }
}
```

## Output

```shell
17-05-24 21:45:39:566 INFO  AnnotationConfigApplicationContext:582 - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@3b94d659: startup date [Wed May 24 21:45:39 CDT 2017]; root of context hierarchy
17-05-24 21:45:39:724 INFO  MyBean:10 - doing something
```