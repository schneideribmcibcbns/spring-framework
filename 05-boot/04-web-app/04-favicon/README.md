# Using Custom Favicon

In Spring Boot application, we can specify a custom favicon by placing favicon.ico (exactly with this name) in any of the static content locations.

## Our custom favicon.ico

**src/main/resources/favicon.ico**

## A MVC controller

```java
@Controller
public class MyController {

  @RequestMapping("/app")
  public String handler(Model model){
      model.addAttribute("msg", "a message from controller");
      return "my-page";
  }
}
```

## The JSP page

**src/main/webapp/WEB-INF/pages/my-page.jsp**

```jsp
<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
    <h2>A JSP page </h2>
    Message : ${msg}
</body>
</html>
```

## Boot properties

**src/main/resources/application.properties**

spring.mvc.view.prefix= /WEB-INF/pages/
spring.mvc.view.suffix= .jsp

## Main class

```java
@SpringBootApplication
public class ExampleMain{

  public static void main(String[] arg) {
      SpringApplication.run(ExampleMain.class, arg);
  }
}
```

## Output

![module](images/output.png)

As seen in above screenshot, our custom icon is showing up instead of the default leaf icon.