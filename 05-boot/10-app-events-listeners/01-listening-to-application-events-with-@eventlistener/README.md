# Listening to Application Events with @EventListener

In this quick example, we will use Spring core annotation `@EventListener` to see what application events are fired during start up.

```java
@Component
public class ListenerBean {
  @EventListener
  public void handleEvent(Object event) {
      System.out.println("event: "+event);
  }
}
```

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) throws InterruptedException {
      SpringApplication sa = new SpringApplication(ExampleMain.class);
      ConfigurableApplicationContext context = sa.run(args);
      MyBean bean = context.getBean(MyBean.class);
      bean.doSomething();
  }
}
```

**Output**

<pre>
2017-09-07 11:38:38.988  INFO 8212 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@6d3af739: startup date [Thu Sep 07 11:38:38 CDT 2017]; root of context hierarchy
<br>2017-09-07 11:38:39.370  INFO 8212 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
<br><span style="color:green">event: org.springframework.context.event.ContextRefreshedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@6d3af739: startup date [Thu Sep 07 11:38:38 CDT 2017]; root of context hierarchy]</span>
<br><span style="color:green">event: org.springframework.boot.context.event.ApplicationReadyEvent[source=org.springframework.boot.SpringApplication@14fc5f04]</span>
<br><span style="color:purple">-- in doSomething() --</span>
<br>2017-09-07 11:38:39.379  INFO 8212 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing <br>org.springframework.context.annotation.AnnotationConfigApplicationContext@6d3af739: startup date [Thu Sep 07 11:38:38 CDT 2017]; root of context hierarchy
<br><span style="color:green">event: org.springframework.context.event.ContextClosedEvent[source=org.springframework.context.annotation.AnnotationConfigApplicationContext@6d3af739: startup date [Thu Sep 07 11:38:38 CDT 2017]; root of context hierarchy]</span>
<br>2017-09-07 11:38:39.380  INFO 8212 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown
</pre>

In above output, the event `ApplicationReadyEvent` is Spring Boot specific event, which is sent after the context refresh and any related callbacks have been processed to indicate the application is ready to service requests.

Spring boot fires some more additional application events. Some events are actually triggered before the `ApplicationContext` is created so we cannot receive them using above bean based listener. In the next example, we will see how can we listen to those additional events.