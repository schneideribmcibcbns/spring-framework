# Auto restart using spring-boot-devtools

While making new changes to our application, we can make boot to restart automatically. For that we need to include an extra maven dependency: spring-boot-devtools.

## How that works?

During runtime, Spring boot monitors the folder which are in classpath (in maven, the folders which are under 'target' folder). We just need to trigger compilation of the sources on changes which will cause to update the 'target' folder and Spring boot will automatically restart the application. If we are using Eclipse IDE then save action can trigger the compilation. In Intellij we can use Ctrl + F9 to recompile.

Spring uses a custom classloader to restart the application. It's also good to know following lines from Spring boot reference doc:

> The restart technology provided by Spring Boot works by using two classloaders. Classes that don't change (for example, those from third-party jars) are loaded into a base classloader. Classes that you're actively developing are loaded into a restart classloader. When the application is restarted, the restart classloader is thrown away and a new one is created. This approach means that application restarts are typically much faster than "cold starts" since the base classloader is already available and populated.

## Example

Among other necessary boot application dependencies, add the following additional dependency in pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

Now create a very simple Spring MVC application.

```java
@SpringBootApplication
public class ExampleMain {

  public static void main(String[] args) {
      SpringApplication.run(ExampleMain.class, args);
  }
}
```

```java
@Controller
public class MyController {

  @RequestMapping("/test")
  public String handle(Model model) {
      model.addAttribute("msg", "a message from controller");
      return "testView";
  }
}
```

**src/main/webapp/WEB-INF/pages/testView1.jsp**

```html
<html>
<body>
    <h4>JSP Page</h4>
    Message : ${msg}
</body>
</html>
```

## Run spring maven plugin:

It does not matter if we run it from command line or from IDE, spring just watches the target folder during runtime. I prefer to run it from command line because it's easier to see restart activity in command window.

## Output

Now keep the application running and make some changes to the project code, let's add a new handler method with different mapping:

```java
@Controller
public class MyController {

  @RequestMapping("/test")
  public String handle(Model model) {
      model.addAttribute("msg", "a message from controller");
      return "testView";
  }

  @RequestMapping("/test2")
  public String handle2(Model model) {
      model.addAttribute("msg2", "a message from controller 2");
      return "testView2";
  }
}
```

Add new JSP file.

**src/main/webapp/WEB-INF/pages/testView2.jsp**

```html
<html>
<body>
    <h4>JSP Page 2</h4>
    Message : ${msg2}
</body>
</html>
```

In eclipse we just need to save the changes. In Intellij either press Ctrl+F9 or we have to select option 'Make project automatically' under compiler settings which will trigger compilation automatically (for that option to work, the application must be running externally e.g. from command line as we mentioned before).

On the command line, we will see application is restarted as we make changes. This is very useful feature, it saves time and a lot of clicks/key-presses during development time.

## spring-boot-devtools is disabled in production

> Developer tools are automatically disabled when running a fully packaged application. If your application is launched using java -jar or if it's started using a special classloader, then it is considered a "production application". Flagging the dependency as optional is a best practice that prevents devtools from being transitively applied to other modules using your project. Gradle does not support optional dependencies out-of-the-box so you may want to have a look to the propdeps-plugin in the meantime.