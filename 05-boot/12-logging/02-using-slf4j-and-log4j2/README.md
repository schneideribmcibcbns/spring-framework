# Using SLF4J with Log4j2 in Spring Boot

This example shows how to use SLF4J API with Log4j2 implementation in a Spring Boot application.

## pom.xml dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
</dependencies>
```

As we have seen in the [last tutorial](../01-default-logging-config/README.md), spring-boot-starter excludes the default dependency of `commons-logging` (JCL) and includes the dependency of `spring-boot-starter-logging` (which among other configurations uses `logback` as logging implementation). As in this example we want to use `log4j2` instead of `logback`, we need to exclude the dependency of `spring-boot-starter-logging` in our `pom.xml` above.

In our `pom.xml` above, we have also added the dependency of `spring-boot-starter-log4j2`, let's see what dependencies that starter includes.

### spring-boot-starter-log4j2/pom.xml

`spring-boot-starter-log4j2` is a starter for using `Log4j2`. It is an alternative to `spring-boot-starter-logging`.

```xml
<dependencies>
	<dependency>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-slf4j-impl</artifactId>
	</dependency>
	<dependency>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-api</artifactId>
	</dependency>
	<dependency>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-core</artifactId>
	</dependency>
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>jcl-over-slf4j</artifactId>
	</dependency>
	<dependency>
		<groupId>org.slf4j</groupId>
		<artifactId>jul-to-slf4j</artifactId>
	</dependency>
</dependencies>
```

Following is a quick description of above artifacts:

**log4j-slf4j-impl**

It is log4j2 binding to slf4j so that we can use slf4j API and log4j2 as logging implementation. Check out [slf4j quick features](https://www.logicbig.com/tutorials/misc/java-logging/slf4j.html) to understand why this binding is needed.

**log4j-api**

It provides the API which can be used directly in applications to log messages (as opposed to log4j, log4j2 separates logging API from it's implementations) . This dependency also provides the adapter components required to hook up log4j2 implementations.

**log4j-core**

This is Apache Log4j2 Implementation.

**jcl-over-slf4j**

This dependency bridges Apache Commons Logging (JCL) to SLF4J layer. This is needed for Spring framework internal logging code which uses JCL API. Per our configuration logs sent to JCL will be redirected to SLF4J which will ultimately use log4j2 to output the logs. Also check out [this example](../../../01-core/10-logging/04-logging-in-slf4j/README.md) to understand how that bridging work.

**jul-to-slf4j**

This dependency bridges Java Util Logging (JUL) to SLF4J. So all logs sent from JUL API will redirect to SLF4J and then log4j2.

## Example

In following example, we will use SLF4J API. Per above configuration, all logs from SLF4j and even from Spring internal logs (sent from JCL) will end up in log4j output. We are also going to include log4j2 configuration (log4j2-spring.xml) to customize the output format.

### Logging with SLF4J API

```java
package com.logicbig.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyBean {
  Logger logger = LoggerFactory.getLogger(MyBean.class);

  public void doSomething() {
      logger.info("some message");
  }
}
```

### Log4j2 configuration

**src\main\resources\log4j2-spring.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout
                    pattern="[%d{MM:dd HH:mm:ss.SSS}] [%level] [%logger{36}] - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>
```

Spring Boot recommends to use the -spring variants for logging configuration file names (for example log4j2-spring.xml rather than log4j2.xml). It helps Spring to completely take control of the logging framework initialization and to avoid its initialization before the ApplicationContext is created.

### Boot properties

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
[09:21 13:14:58.557] [INFO] [com.logicbig.example.ExampleMain] - Starting ExampleMain on JoeMchn with PID 9372 (D:\example-projects\spring-boot\boot-log4j2-logging\target\classes started by Joe in D:\example-projects\spring-boot\boot-log4j2-logging)
[09:21 13:14:58.558] [INFO] [com.logicbig.example.ExampleMain] - No active profile set, falling back to default profiles: default
[09:21 13:14:58.603] [INFO] [org.springframework.context.annotation.AnnotationConfigApplicationContext] - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@2f9f7dcf: startup date [Thu Sep 21 13:14:58 CDT 2017]; root of context hierarchy
[09:21 13:14:58.969] [INFO] [org.springframework.jmx.export.annotation.AnnotationMBeanExporter] - Registering beans for JMX exposure on startup
[09:21 13:14:58.979] [INFO] [com.logicbig.example.ExampleMain] - Started ExampleMain in 0.646 seconds (JVM running for 1.077)
[09:21 13:14:58.980] [INFO] [com.logicbig.example.MyBean] - some message
[09:21 13:14:58.981] [INFO] [org.springframework.context.annotation.AnnotationConfigApplicationContext] - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@2f9f7dcf: startup date [Thu Sep 21 13:14:58 CDT 2017]; root of context hierarchy
```

As seen above, all logs have date format starting from 'MM:dd' as we specified in our log4j2-spring.xml file.