# Generating Random Properties

`RandomValuePropertySource` can generate random values for any properties that starts with "random.". This `PropertySource` is automatically applied on finding property values of format "${random.xyz..}.

**The external properties - src/main/resources/application.properties**

```shell
#random int
app.location-x=${random.int}
app.location-y=${random.int}

#random int with max
app.user-age=${random.int(100)}

#random int range
app.max-users=${random.int[1,10000]}

#random long with max
app.refresh-rate-milli=${random.long(1000000)}

#random long range
app.initial-delay-milli=${random.long[100,90000000000000000]}

#random 32 bytes
app.user-password=${random.value}

#random uuid. Uses java.util.UUID.randomUUID()
app.instance-id=${random.uuid}
```

**@ConfigurationProperties class**

```java
@Component
@ConfigurationProperties("app")
public class MyAppProperties {
  private int locationX;
  private int locationY;
  private int userAge;
  private int maxUsers;
  private long refreshRateMilli;
  private long initialDelayMilli;
  private String userPassword;
  private UUID instanceId;
    .............
}
```

Note that, we can also use `@Value` instead of `@ConfigurationProperties` to access random properties.

**The Main class**

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      SpringApplication springApplication = new SpringApplication(ExampleMain.class);
      springApplication.setBannerMode(Banner.Mode.OFF);
      springApplication.setLogStartupInfo(false);
      ConfigurableApplicationContext context = springApplication.run(args);
      MyAppProperties bean = context.getBean(MyAppProperties.class);
      System.out.println(bean);
  }
}
```

**Output**

```log4j
2018-03-04 18:50:57.116  INFO 4960 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 18:50:57 EST 2018]; root of context hierarchy
2018-03-04 18:50:57.494  INFO 4960 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
MyAppProperties{
 locationX=371686636,
 locationY=1264791483,
 userAge=62,
 maxUsers=7605,
 refreshRateMilli=600621,
 initialDelayMilli=72076927485481040,
 userPassword='5041a204a2f9349aa661f79e4d2fdf12',
 instanceId=73fdbd40-0625-43f0-b25c-ddf690015df6
}
2018-03-04 18:50:57.505  INFO 4960 --- [       Thread-3] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@28c4711c: startup date [Sun Mar 04 18:50:57 EST 2018]; root of context hierarchy
2018-03-04 18:50:57.507  INFO 4960 --- [       Thread-3] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
```