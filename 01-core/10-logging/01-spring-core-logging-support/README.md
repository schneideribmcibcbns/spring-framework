# Spring core logging support

Spring implicitly provides Jakarta (Apache) Commons Logging API (JCL) support for the client applications. We do not have to include JCL dependency in our projects as it's already included in Spring core module. Spring itself uses JCL internally.

## What is JCL?

JCL wraps a third party log implementation around it's own API.

We can provide an implementation dependency (like Log4J) in our project. JCL uses a runtime discovery algorithm that looks for other logging frameworks in the classpath.

We can also specify an explicit logging implementation in a `commons-logging.properties` instead of relying on auto discovery mechanism.

By default, JCL uses Java Util logging (JUL) as the log implementation.

In this tutorial, we will see an example of using JCL API and the output from java Util logging (the default).

## Example

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

**Output**

```shell
May 24, 2017 9:18:55 PM org.springframework.context.annotation.AnnotationConfigApplicationContext prepareRefresh
INFO: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@5d099f62: startup date [Wed May 24 21:18:55 CDT 2017]; root of context hierarchy
May 24, 2017 9:18:56 PM com.logicbig.example.MyBean doSomething
INFO: doing something
```

Above output shows Java Util (JUL) default logs even though we are using JCL API.

## Customizing Java Util Logging

Let's a add JUL specific `logging.properties` with some customization:

**src/main/resources/logging.properties**

```shell
handlers= java.util.logging.ConsoleHandler
.level= INFO
java.util.logging.ConsoleHandler.level = INFO
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter
java.util.logging.SimpleFormatter.format=[%1$tF %1$tT] [%4$s] {%2$s} %5$s %n
```

Let's load the properties file and run spring application.

```java
@Configuration
public class ExampleMain2 {

  @Bean
  public MyBean myBean() {
      return new MyBean();
  }

  public static void main(String[] args) {
      String path = ExampleMain2.class.getClassLoader()
                                     .getResource("logging.properties")
                                     .getFile();
      System.setProperty("java.util.logging.config.file", path);

      ConfigurableApplicationContext context =
              new AnnotationConfigApplicationContext(ExampleMain2.class);

      MyBean bean = context.getBean(MyBean.class);
      bean.doSomething();
  }
}
```

**Output**

```shell
[2017-05-24 21:53:20] [INFO] {org.springframework.context.annotation.AnnotationConfigApplicationContext prepareRefresh} Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@179d3b25: startup date [Wed May 24 21:53:20 CDT 2017]; root of context hierarchy 
[2017-05-24 21:53:20] [INFO] {com.logicbig.example.MyBean doSomething} doing something 
Note that spring internal logging is also customized in the output above.
```