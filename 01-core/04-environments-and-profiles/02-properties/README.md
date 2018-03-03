# Environment Properties

Spring attempts to unify all name/value property pairs access into org.springframework.core.env.Environment.

The properties source can be java.util.Properties, loaded from a file or Java system/env properties or java.util.Map.

If we are in the Servlet container environment, the source can be javax.servlet.ServletContext or javax.servlet.ServletConfig.

## Accessing properties with Environment

Using methods of org.springframework.core.env.Environment directly is one way to access properties in our applications.

Following example lists property sources names, and Java System/Env properties

```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class DefaultSystemSourcesExample {
    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext();
        ConfigurableEnvironment env = context.getEnvironment();
        printSources(env);
        System.out.println("---- System properties -----");
        printMap(env.getSystemProperties());
        System.out.println("---- System Env properties -----");
        printMap(env.getSystemEnvironment());
    }

    private static void printSources (ConfigurableEnvironment env) {
        System.out.println("---- property sources ----");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println("name =  " + propertySource.getName() + "\nsource = " + propertySource
                                .getSource().getClass()+"\n");
        }
    }

    private static void printMap (Map<?, ?> map) {
        map.entrySet()
           .stream()
           .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));

    }
}
```

### Output:

```shell
---- property sources ----
name =  systemProperties
source = class java.util.Properties

name =  systemEnvironment
source = class java.util.Collections$UnmodifiableMap

---- System properties -----
java.runtime.name = Java(TM) SE Runtime Environment
sun.boot.library.path = C:\Program Files\Java\jdk1.8.0_65\jre\bin
java.vm.version = 25.65-b01
java.vm.vendor = Oracle Corporation
java.vendor.url = http://java.oracle.com/
.....

---- System Env properties -----
configsetroot = C:\WINDOWS\ConfigSetRoot
PROCESSOR_LEVEL = 6
FP_NO_HOST_CHECK = NO
FPS_BROWSER_APP_PROFILE_STRING = Internet Explorer
LOGONSERVER = \\MicrosoftAccount
JAVA_HOME = C:\Program Files\Java\jdk1.8.0_65
.....
Process finished with exit code 0
```

By default, system properties have precedence over environment variables, so if the foo property happens to be set in both places during a call to env.getProperty("foo"), the system property value will 'win' and be returned preferentially over the environment variable. Note that property values will not get merged but rather completely overridden by a preceding entry.

## Using annotation @PropertySource

The org.springframework.context.annotation.PropertySource annotation provides a convenient and declarative mechanism for adding a PropertySource to Environment:

** src/main/resources/app.properties **

```shell
some-strProp=strProp-value
```

```java
@Configuration
@PropertySource("classpath:app.properties")
public class PropertySourceExample {

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(PropertySourceExample.class);
        ConfigurableEnvironment env = context.getEnvironment();
        System.out.println("some-strProp value is " + property);

        //printing all sources
        System.out.println(env.getPropertySources());
    }
}
```

** Output: **

```shell
some-strProp value is strProp-value
[systemProperties,systemEnvironment,class path resource [app.properties]]
```

## Injecting Environment

We can also inject Environment object as a Bean to access properties:

```java
@Configuration
@PropertySource("classpath:app.properties")
public class PropertySourceBeanExample {
    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                      new AnnotationConfigApplicationContext(
                                      PropertySourceBeanExample.class);
        context.getBean(MyBean.class).showProp();
    }


    public static class MyBean {
        @Autowired
        Environment env;

        public void showProp () {
            System.out.println(env.getProperty("some-strProp"));
        }
    }
}
```

The @PropertySource 'value' element can have absolute path of the property file as well:

```shell
@PropertySource("file:absolute-path/app.properties")
```

## Injecting Properties using @Value annotation

For injecting properties by their names, we can use the placeholder ${....} as value element of @Value annotation. We also have to register PropertySourcesPlaceholderConfigurer as bean:

```
@Configuration
@PropertySource("classpath:app.properties")
public class BeanValueExample {
    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(BeanValueExample.class);
        context.getBean(MyBean.class).showProp();
    }

    public static class MyBean {
       @Value("${some-prop:defaultStr}")
       private String str;


        public void showProp () {
            System.out.println(str);
        }
    }
}
```

The @Value annotation can be placed on fields, methods and constructors parameters to inject properties

We can specify a default value followed by colon, which will be used if the target property not found:

```java
@Value("${some-prop:defaultStr}")
```

In cases where a given property key exists in more than one .properties file, the last @PropertySource annotation processed will 'win' and override.

See also: [PropertySource Java doc](http://docs.spring.io/spring/docs/current/javadoc-api/index.html?org/springframework/context/annotation/PropertySource.html)

## Using Spring expression language

We can use spring expression as value element of @Value. In that case we don't have to set up PropertySource or PropertySourcesPlaceholderConfigurer:

```java
@Configuration
public class BeanExprExample {
    @Bean
    public MyBean myBean () {
        return new MyBean();
    }

    public static void main (String[] args) {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(BeanExprExample.class);
        context.getBean(MyBean.class)
               .showProp();
    }

    public static class MyBean {
        @Value("#{systemProperties['user.home']}")
        private String userHome;

        public void showProp () {
            System.out.println(userHome);
        }
    }
}
```