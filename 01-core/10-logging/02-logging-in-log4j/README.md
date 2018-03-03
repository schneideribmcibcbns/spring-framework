# Logging in Log4j

## Log4j dependency

To use Log4j with the JCL API (the default spring logging dependency), we need to add Log4j dependency:

```xml
 <dependency>
   <groupId>log4j</groupId>
   <artifactId>log4j</artifactId>
   <version>1.2.17</version>
  </dependency>
```

## log4j.properties

We also have to provide `log4j.properties` in classpath.

**src/main/resources/log4j.properties**

```shell
log4j.rootCategory=INFO, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yy-MM-dd HH:mm:ss:SSS} %5p %t %c{2}:%L - %m%n
```

Different log formatting options are [provided here](https://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/PatternLayout.html).

## Example Bean

```java
package com.logicbig.example;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
17-05-24 21:12:26:826  INFO main annotation.AnnotationConfigApplicationContext:582 - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@1376c05c: startup date [Wed May 24 21:12:26 CDT 2017]; root of context hierarchy
17-05-24 21:12:27:014  INFO main example.MyBean:10 - In MyBean#doSomething() method
```

## Directly Using Log4j API

Instead of using JCL API, we can use Log4j API directly:

```java
package com.logicbig.example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class MyBean {
  private static final Logger log = LogManager.getLogger(MyBean.class);

  public void doSomething() {
      log.info("doing something");
  }
}
```

The output will be same. In this case since our code is directly invoking log4j API, JCL will not be involved at all, but Spring internal logging will still go through JCL layer as they are still using JCL API.