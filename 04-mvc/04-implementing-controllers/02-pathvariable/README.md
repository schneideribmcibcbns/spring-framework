# Binding URI Template Variable with @PathVariable

The @PathVariable annotation is used with a handler method parameter to capture the value of a URI template variable.

## @PathVariable with a Variable Name

@PathVariable has only one element 'value' that is used to define URI template variable name.

```java
@Controller
@RequestMapping("users")
public class UserController {

    @RequestMapping("{id}")
    public String handleRequest (@PathVariable("id") String userId, Model map) {
        map.addAttribute("msg", "User id " + userId);
        return "my-page";
    }
}
```

## @PathVaraible without a Variable Name

@PathVariable 'value' element is optional. If the URI template variable name matches the method parameter name we can skip the variable name. This only works if the code is compiled with debugging information (variable names must be included in the debugging information), Spring MVC will match the method parameter name to the URI template variable name.:

```java
@Controller
@RequestMapping("users")
public class UserController {

    @RequestMapping("profiles/{userName}")
    public String handleRequest2 (@PathVariable String userName, Model model) {
        model.addAttribute("msg", "user profile name : " + userName);
        return "my-page";
    }
}
```

## Using Multiple @PathVariable Annotations

A method can have any number of @PathVariable annotations:

```java
@Controller
@RequestMapping("users")
public class UserController {

    @RequestMapping("{id}/posts/{postId}")
    public String handleRequest3 (@PathVariable("id") String userId,
                                  @PathVariable("postId") String postId,
                                  Model model) {
        model.addAttribute("msg", "user id : " + userId + ", post id: " + postId);
        return "my-page";

    }
}
```

## Using Map with @PathVariable for Multiple Variables

If the method parameter is Map<String, String> or MultiValueMap<String, String> then the map is populated with all path variable names and values.

```java
@Controller
@RequestMapping("users")
public class UserController {

    @RequestMapping("{id}/messages/{msgId}")
    public String handleRequest4 (@PathVariable Map<String, String> varsMap, Model model) {
        model.addAttribute("msg", varsMap.toString());
        return "my-page";
    }
}
```

## Template Variables with Different Names are Ambiguous (same base URI)

Defining different template variable names does not define different URI paths. Following example will cause runtime exception, complaining about ambiguous mapping.

```java
@Controller
@RequestMapping("/employees")
public class EmployeeController {

    @RequestMapping("{id}")
    public String handleRequest(@PathVariable("id") String userId, Model model){
        model.addAttribute("msg", "employee id: "+userId);
        return "my-page";
    }

   @RequestMapping("{employeeName}")
    public String handleRequest2 (@PathVariable("employeeName") String userName,
                                                                   Model model) {
        model.addAttribute("msg", "employee name : " + userName);
        return "my-page";
    }
} 
```

output:

```shell
java.lang.IllegalStateException: Ambiguous handler methods mapped for HTTP path 'http://localhost/employees/234': {public java.lang.String com.logicbig.example.EmployeeController.handleRequest2(java.lang.String,org.springframework.ui.Model), public java.lang.String com.logicbig.example.EmployeeController.handleRequest(java.lang.String,org.springframework.ui.Model)}
at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod(AbstractHandlerMethodMapping.java:375)
at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:322)
at org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:60)
```

## Using Regex in Template Variables to Avoid Ambiguity (same base URI)

If we use mutually exclusive regex in @RequestMapping then Spring can select one path depending on the request, even if their base URL path are same (like the last example, where we had ambiguous mapping exception)

```java
/**
 * Using mutually exclusive regex, which can be used
 * to avoid ambiguous mapping exception
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @RequestMapping("{id:[0-9]+}")
    public String handleRequest(@PathVariable("id") String userId, Model model){
        model.addAttribute("msg", "profile id: "+userId);
        return "my-page";

    }

    @RequestMapping("{name:[a-zA-Z]+}")
    public String handleRequest2 (@PathVariable("name") String deptName, Model model) {
        model.addAttribute("msg", "dept name : " + deptName);
        return "my-page";
    }
}
```

## Project

Try the following URLs:

http://localhost:8080/spring-mvc-path-variable/users/234
http://localhost:8080/spring-mvc-path-variable/users/profiles/joe
http://localhost:8080/spring-mvc-path-variable/users/234/posts/143
http://localhost:8080/spring-mvc-path-variable/users/234/messages/453
http://localhost:8080/spring-mvc-path-variable/users/234/posts/143
http://localhost:8080/spring-mvc-path-variable/dept/234
http://localhost:8080/spring-mvc-path-variable/dept/account
http://localhost:8080/spring-mvc-path-variable/employees/131431