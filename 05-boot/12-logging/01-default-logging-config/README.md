# Understanding Spring Boot default logging Configuration

By default, Spring Boot redirects all logs sent from Apache Commons Logging (JCL), Log4J and Java Util Logging (JUL) APIs to SLF4J. Logback is used as SLF4J implementation.

If not already familiar then check out quick SLF4J features [here](https://www.logicbig.com/tutorials/misc/java-logging/slf4j.html).

Logback natively implements SLF4J API. Spring internal code uses JCL, that will also end up in Logback logs. Spring Boot achieves that with `spring-boot-starter-logging` dependencies, which is transitively included by all starters.

## spring-boot-starter

This starter is the core starter which contains Spring core, logging and other dependencies. This starter dependency is included by spring-boot-starter-web (Spring web MVC) and most of the other Spring Boot's starters. Let's see what related dependencies are setup in `spring-boot-starter` (1.5.6.RELEASE):

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-logging</artifactId>
</dependency>
<dependency>
 <groupId>org.springframework</groupId>
 <artifactId>spring-core</artifactId>
 <exclusions>
   <exclusion>
     <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
  </exclusion>
 </exclusions>
</dependency>
```

As seen above, spring-boot-starter excludes commons-logging (JCL) dependency. So all JCL logging code written in Spring internally will end up in `NoClassDefFoundError`. To fix that `spring-boot-starter-logging` adds other dependencies.

## spring-boot-starter-logging

Let's see what are the dependencies included in `spring-boot-starter-logging` (1.5.6.RELEASE):

```xml
<dependencies>
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jul-to-slf4j</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>log4j-over-slf4j</artifactId>
    </dependency>
</dependencies>
```

### logback-classic

Logback classic implements SLF4J natively. All Spring boot logs, by default, will use logback logging system in Spring Boot.

### jcl-over-slf4j

This dependency bridges Apache Commons Logging (JCL) to SLF4J layer. This also fixes the `NoClassDefFoundError` we mentioned above. This bridge routes all log messages sent from JCL to SLF4J which will ultimately use logback to output the logs. Also check out this [example](../../../01-core/10-logging/04-logging-in-slf4j/README.md) to understand how that bridging work.

### jul-to-slf4j

This dependency bridges Java Util Logging (JUL) to SLF4J. So all logs sent from JUL API will redirect to SLF4J and then logback. check out this [example](https://www.logicbig.com/tutorials/misc/java-logging/jul-to-slf4j.html) as well.

### log4j-over-slf4j

This dependency bridges Log4J to SLF4J, So all logs sent from Log4J API will route to SLF4J and then logback.

## Example

In following example, we will use Java Util Logging (JUL) API. As explained above, all logs from JUL and even Spring internal logs (sent from JCL) will end up in logback output. We are also going to include logback configuration (`logback.xml`) to customize the output a little. Other than that, we will not add any logging configurations/dependencies and will use the default ones.

### Logging with JUL

```java
package com.logicbig.example;

import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class MyBean {
  private static final Logger logger = Logger.getLogger(MyBean.class.getName());

  public void doSomething() {
      System.out.println("-- in doSomething() --");
      logger.info("some message");
  }
}
```

### Logback configuration

**src\main\resources\logback.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="stdout" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yy-MMMM-dd HH:mm:ss:SSS} %5p %t %c{2}:%L - %m%n</pattern>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="stdout"/>
    </root>
</configuration
```

### Spring configuration

**src/main/resources/application.properties**

```shell
spring.main.banner-mode=off
```
 
### The Main class

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      ConfigurableApplicationContext context =
              SpringApplication.run(ExampleMain.class, args);
      MyBean bean = context.getBean(MyBean.class);
      bean.doSomething();
  }
}
```

### Output

```shell
17-September-12  15:21:06:217  INFO main c.l.e.ExampleMain:48 - Starting ExampleMain on JoeMsi with PID 7644 (D:\example-projects\spring-boot\boot-default-logging\target\classes started by Joe in D:\example-projects\spring-boot\boot-default-logging)
17-September-12  15:21:06:219  INFO main c.l.e.ExampleMain:593 - No active profile set, falling back to default profiles: default
17-September-12  15:21:06:253  INFO main o.s.c.a.AnnotationConfigApplicationContext:583 - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@10b48321: startup date [Mon Sep 11 15:21:06 CDT 2017]; root of context hierarchy
17-September-12  15:21:06:658  INFO main o.s.j.e.a.AnnotationMBeanExporter:431 - Registering beans for JMX exposure on startup
17-September-12  15:21:06:667  INFO main c.l.e.ExampleMain:57 - Started ExampleMain in 0.619 seconds (JVM running for 0.92)
-- in doSomething() --
17-September-12  15:21:06:669  INFO main c.l.e.MyBean:12 - some message
17-September-12  15:21:06:670  INFO Thread-2 o.s.c.a.AnnotationConfigApplicationContext:984 - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@10b48321: startup date [Mon Sep 11 15:21:06 CDT 2017]; root of context hierarchy
17-September-12  15:21:06:670  INFO Thread-2 o.s.j.e.a.AnnotationMBeanExporter:449 - Unregistering JMX-exposed beans on shutdown
```

As seen above, all logs have two digits for year (yy) and complete month name (MMMM) as we specified in our `logback.xml`.
