# Triggering auto-restart based on a single file

Spring Boot devtools provides an option to specify a 'trigger file'. That means, this will be the only file which will kick off the application restart if we modify it. This is good when we don't want to restart frequently on each file modification.

To use this option, we need to add the following application property:

```apache
spring.devtools.restart.trigger-file=<my-file-name>
```

Let's see an example how to do that.

## Creating Spring boot MVC application

```java
@Controller
public class MyController1 {

  @RequestMapping("/path1")
  public String handle(Model model) {
      model.addAttribute("msg", "from MyController1");
      return "testView1";
  }
}
```

**src/main/webapp/WEB-INF/pages/testView1.jsp**

```html
<html>
  <body>
    <h3>Page 1</h3>
   <p> ${msg} </p>
  </body>
</html>
```

```java
@Controller
public class MyController2 {

  @RequestMapping("/path2")
  public String handle(Model model) {
      model.addAttribute("msg", "from MyController2");
      return "testView2";
  }
}
```

**src/main/webapp/WEB-INF/pages/testView2.jsp**

```html
<html>
  <body>
    <h3>Page 2</h3>
   <p> ${msg} </p>
  </body>
</html>
```

Let's say we want to have MyController2.java as the trigger file:

**src/main/resources/Application.properties**

```apache
spring.mvc.view.prefix= /WEB-INF/pages/
spring.mvc.view.suffix= .jsp
spring.devtools.restart.additional-paths=.
spring.devtools.restart.trigger-file=MyController2.java
```

The main class:

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) {
      SpringApplication.run(ExampleMain.class, args);
  }
}
```

## Run the application:

Access the pages at /path1 and /path2. Modifying any file other than MyController2.java will not trigger the restart as shown in this video: