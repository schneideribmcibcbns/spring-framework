# Auto browser refresh with LiveReload

In addition to auto restart feature of spring-boot-devtools, LiveReload is another very useful feature which triggers a browser refresh when there are some changes in the code or in a resource file. Let's see an example to use LiveReload.

## Installing LiveReload browser extension

We have to [install LiveReload browser extension](http://livereload.com/extensions/) to use LiveReload. After installing LiveReload in chrome browser (for example), you will see an icon like this:

![module](images/output.png)

Also after starting our spring boot application we have to make sure that this tool is enabled or not. Hover over the mouse pointer on the LiveReload circular icon and if we see 'Enable LiveReload' tooltip like this then we have to click the icon to enable it.

![module](images/output.png)

![module](images/output.png)

## Creating a simple Spring Boot MVC project

```java
@Controller
public class MyController {

  @RequestMapping("/test")
  public String handle(Model model) {
      model.addAttribute("msg", "test");
      return "testView";
  }
}
```

**src/main/webapp/WEB-INF/pages/testView.jsp**

```html
<html>
<body>
<h3>${msg}</h3>
</body>
</html>
```

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) {
      SpringApplication.run(ExampleMain.class, args);
  }
}
```

**src/main/resources/application.properties**

```apache
spring.mvc.view.prefix= /WEB-INF/pages/
spring.mvc.view.suffix= .jsp
spring.devtools.restart.additional-paths=.
```

Note that we have included a new property of `spring.devtools.restart.additional-paths` this time. Spring boot watches all files which are in 'target' folder (if using maven). In exploded form, we don't have all resources copied to the target folder. That's the reason we have to include additional folders to be watched e.g. JSP files. The single dot points to the application base directory.

## Output

Following video shows how auto-restart and LiveReload work without explicitly restarting the application and refreshing the browser. In this example, I'm using IntelliJ.

Note that in intellij 'Make project automatically' option should be selected otherwise we have to use Ctrl+F9 for compilation:

![module](images/output2.png)

In Eclipse, we have to save (Ctrl+s or Ctrl+Shift+s) to trigger the compilation.