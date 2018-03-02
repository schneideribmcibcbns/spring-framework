# Listening to HTTP request as events

In Spring web application, a bean can register RequestHandledEvent to get notifications that an HTTP request has been serviced. This event is published after the request is complete. Let's see how to do that with example.

```java
RequestHandledEvent Example
@Component
public class MyBean {
  
  @EventListener
  public void handleEvent (RequestHandledEvent e) {
      System.out.println("-- RequestHandledEvent --");
      System.out.println(e);
  }
}
```

```java
@Controller
public class MyWebController {
  
  @RequestMapping("/test")
  @ResponseBody
  public String handle () {
      return "test response from /test";
  }
  
  @RequestMapping("/test2")
  @ResponseBody
  public String handle2 () {
      return "test response from /test2";
  }
}
```

```java
@SpringBootApplication
public class ExampleMain {
  
  public static void main (String[] args) {
      SpringApplication.run(ExampleMain.class, args);
  }
}
```

Access following URL in your browser:

http://localhost:8080/test

http://localhost:8080/test2

Output on server console:

```shell
2017-05-03 19:44:57.296  INFO 13360 --- [nio-8080-exec-1] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
2017-05-03 19:44:57.296  INFO 13360 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
2017-05-03 19:44:57.311  INFO 13360 --- [nio-8080-exec-1] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 15 ms
-- RequestHandledEvent --
ServletRequestHandledEvent: url=[/test]; client=[0:0:0:0:0:0:0:1]; method=[GET]; servlet=[dispatcherServlet]; session=[null]; user=[null]; time=[25ms]; status=[OK]
-- RequestHandledEvent --
ServletRequestHandledEvent: url=[/test2]; client=[0:0:0:0:0:0:0:1]; method=[GET]; servlet=[dispatcherServlet]; session=[null]; user=[null]; time=[2ms]; status=[OK]
In above output, we see Servlet Request Handled Event, which is a Servlet-specific subclass of RequestHandledEvent. In portlet environment, Spring framework uses another subclass; PortletRequestHandledEvent.
```