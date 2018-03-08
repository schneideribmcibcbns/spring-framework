# Using SpringApplication.addListeners()

In Spring boot application, adding listener via `SpringApplication.addListeners()` or `SpringApplicationBuilder.listeners()` allows us to listen to application events even before `ApplicationContext` is created. In this quick example, we will see how to do that.

```java
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

  @Override
  public void onApplicationEvent(ApplicationEvent event) {
      System.out.println("event: " + event);
  }
}
```

```
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      SpringApplication sa = new SpringApplication();
      sa.addListeners(new MyApplicationListener());
      sa.setSources(new HashSet<>(Arrays.asList(ExampleMain.class)));
      ConfigurableApplicationContext context = sa.run(args);
      MyBean bean = context.getBean(MyBean.class);
      bean.doSomething();
  }
}
```

**Output**

<pre>
<span style="color:green">event: org.springframework.boot.context.event.ApplicationStartedEvent[source=org.springframework.boot.SpringApplication@402f32ff]</span>
<br><span style="color:green">event: org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent[source=org.springframework.boot.SpringApplication@402f32ff]</span>
<br><span style="color:green">event: org.springframework.boot.context.event.ApplicationPreparedEvent[source=org.springframework.boot.SpringApplication@402f32ff]</span>
<br>2017-09-07 11:36:24.881  INFO 9556 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@7e9a5fbe: startup date [Thu Sep 07 11:36:24 CDT 2017]; root of context hierarchy
<br>2017-09-07 11:36:25.274  INFO 9556 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
<br><span style="color:green">event: org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@7e9a5fbe: startup date [Thu Sep 07 11:36:24 CDT 2017]; root of context hierarchy]</span>
<br><span style="color:green">event: org.springframework.boot.context.event.ApplicationReadyEvent[source=org.springframework.boot.SpringApplication@402f32ff]</span>
<br><span style="color:purple">-- in doSomething() --</span>
<br>2017-09-07 11:36:25.284  INFO 9556 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@7e9a5fbe: startup date [Thu Sep 07 11:36:24 CDT 2017]; root of context hierarchy
<br><span style="color:green">event: org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@7e9a5fbe: startup date [Thu Sep 07 11:36:24 CDT 2017]; root of context hierarchy]</span>
<br>2017-09-07 11:36:25.285  INFO 9556 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown</pre>

In above output, we can see some extra boot specific events are fired. Following is a quick description of those events:

## ApplicationStartedEvent

Event published as early as conceivably possible as soon as a SpringApplication has been started - before the `Environment` or `ApplicationContext` is available, but after the `ApplicationListeners` have been registered.

## ApplicationEnvironmentPreparedEvent

Event published when a `SpringApplication` is starting up and the `Environment` is first available for inspection and modification.

## ApplicationPreparedEvent

Event published as when a `SpringApplication` is starting up and the `ApplicationContext` is fully prepared but not refreshed. The bean definitions will be loaded and the Environment is ready for use at this stage.

## ApplicationReadyEvent

This event is published as late as conceivably possible to indicate that the application is ready to service requests.

## ApplicationFailedEvent

This event is published, if SpringApplication fails to start.