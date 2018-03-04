# Loading application property files from Current Directory

In this example, we will learn how to load Spring Boot application property files from 'current directory' or '/config subdirectory of the current directory' (as stated in [spring boot ref doc](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config-application-property-files)).

Here 'current directory' refers to the location where we execute the application. In this example, we will run the application via spring boot maven plugin. We will also run the executable application jar. Running from an IDE does not work, because an IDE cannot copy (or load) the properties files to the location where we intend to execute our jar.

## Writing a simple Boot application

```java
@SpringBootConfiguration
public class ExampleMain {
    @Bean
    MyBean myBean() {
        return new MyBean();
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication bootApp = new SpringApplication(ExampleMain.class);
        bootApp.setBannerMode(Banner.Mode.OFF);
        bootApp.setLogStartupInfo(false);
        ConfigurableApplicationContext context = bootApp.run(args);
        MyBean myBean = context.getBean(MyBean.class);
        myBean.startApplication();
    }

    private static class MyBean {

        @Value("${app.title}")
        private String appTitle;

        public void startApplication() {
            System.out.printf("-- in MyBean app title: %s --%n", appTitle);
        }
    }
}
```

We are going to put following property file in the maven root location (where the pom.xml file is).

**application.properties**

```shell
app.title=My Spring Application
```

## Running application via Boot gradle plugin

```shell
gradle bootRun
```

Output

```shell
$ gradle bootRun
:compileJava UP-TO-DATE
:processResources NO-SOURCE
:classes UP-TO-DATE
:findMainClass
:bootRun
2018-03-04 16:44:25.389  INFO 8248 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@306279ee: startup date [Sun Mar 04 16:44:25 EST 2018]; root of context hierarchy
-- in MyBean app title: My Spring Application --
2018-03-04 16:44:25.661  INFO 8248 --- [       Thread-1] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@306279ee: startup date [Sun Mar 04 16:44:25 EST 2018]; root of context hierarchy

BUILD SUCCESSFUL in 6s
3 actionable tasks: 2 executed, 1 up-to-date
```

## Packaging the application

```shell
gradle build
```

Output

```
$ gradle build
:compileJava UP-TO-DATE
:processResources NO-SOURCE
:classes UP-TO-DATE
:findMainClass
:jar
:bootRepackage
:assemble
:compileTestJava NO-SOURCE
:processTestResources NO-SOURCE
:testClasses UP-TO-DATE
:test NO-SOURCE
:check UP-TO-DATE
:build
```

## Placing the property file at 'current directory' location

We need to place the `application.properties` where we are going to run the executable jar. So we are going to place it just beside the executable jar (in the gradle build\libs directory):

```shell
cp application.properties build/libs/application.properties
```

## Executing the jar

```shell
$ cd build/libs
$ ls
02-application-property.jar  02-application-property.jar.original  application.properties
$ java -jar 02-application-property.jar
2018-03-04 16:49:56.850  INFO 2148 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@255316f2: startup date [Sun Mar 04 16:49:56 EST 2018]; root of context hierarchy
-- in MyBean app title: My Spring Application --
2018-03-04 16:49:57.164  INFO 2148 --- [       Thread-1] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@255316f2: startup date [Sun Mar 04 16:49:56 EST 2018]; root of context hierarchy
```