# Logging In SLF4j

SLF4J is similar to JCL but has various advantages over JCL (quick features [here](https://www.logicbig.com/tutorials/misc/java-logging/slf4j.html)). This example will demonstrate how to use SLF4J by disabling default JCL. We will use Log4j as an implementation provider for SLF4j.

## Maven dependencies

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>4.3.10.RELEASE</version>
          <exclusions>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>4.3.10.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.8.0-alpha2</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>jcl-over-slf4j</artifactId>
            <version>1.8.0-alpha2</version>
        </dependency>
</dependencies>
```

As seen above, we are excluding commons-logging dependency. By doing so Spring internal logging code will throw following exception:

```shell
Exception in thread "main" java.lang.NoClassDefFoundError: org/apache/commons/logging/LogFactory
```

To fix that, we have included `jcl-over-slf4j` dependency, which provides a drop-in replacement for JCL. It contains exactly same packages and classes as JCL has, but with different code logic. It redirects logging calls to SLF4j layer.

The `slf4j-log4j12` dependency binds the SLF4J API to Log4j implementation. It also pulls Log4j dependency so we don't have to include that separately.

Note that, if we don't exclude `commons-logging` and don't add `jcl-over-slf4j` dependency then we can still use SLF4J API in our code, but Spring internal logging will still be going through JCL, which will ultimately end up Log4j logs, so output will be the same in that case. But that option is not recommended. Also if we use a logging implementation other than Log4J which is supported by SLF4j but not JCL (for example logback) then that might end up in two different formatted output (JCL will switch to its default JUL logger).

## build.gradle

```shell
apply plugin: 'java'
apply plugin: 'eclipse'

repositories {
    mavenCentral();
}

dependencies {
	configurations.all {
    	exclude group: "commons-logging", module: "commons-logging"
	}
	compile 'org.springframework:spring-context:4.3.9.RELEASE'
	compile 'org.slf4j:slf4j-log4j12:1.8.0-alpha2'
   compile 'org.slf4j:jcl-over-slf4j:1.8.0-alpha2'
	
	testCompile ('junit:junit:4.12')
}
```

## A Bean using SLF4J API

```java
package com.logicbig.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBean {
  private static Logger log = LoggerFactory.getLogger(MyBean.class);

  public void doSomething() {
      log.info("doing something");
  }
}
```

## log4j.properties

**src/main/resources/log4j.properties**

```shell
log4j.rootCategory=INFO, stdout
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d{yy-MM-dd HH:mm:ss:SSS} %5p %t %c{2}:%L - %m%n
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
17-09-07 15:58:21:405  INFO main annotation.AnnotationConfigApplicationContext:583 - Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@ea30797: startup date [Thu Sep 07 15:58:21 CDT 2017]; root of context hierarchy
17-09-07 15:58:21:567  INFO main example.MyBean:10 - doing something
```