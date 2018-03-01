# Mapping Requests with @RequestMapping

The annotation @RequestMapping is used to map request URLs to specific controllers.

All elements of @RequestMapping annotation are optional.

It's annotated with @Target(value={METHOD,TYPE}), so it can be used on class level or method level. If @RequestMapping specifies a path on class level then all paths in the methods are relative. @RequestMapping on the class level is not required. Without it, all paths are simply absolute, and not relative

The handler methods without @RequestMapping won't be mapped, even their enclosing class has @Controller and a valid @RequestMapping annotations.

## Elements of @RequestMapping

### String[] value

It's the URL mapping expression:

```java
 @RequestMapping("/users")
 @Controller
 public class UserController{
    ...
 }
```

Multiple URLs can be specified:

```java
 @RequestMapping({"/users", "/clients"})
 @Controller
 public class UserController{
    ...
 }
```

It may contain URI templates:

```java
 @RequestMapping("/users")
 @Controller
 public class UserController{
   @RequestMapping("/{userId}")
    public String handle(....){
     ....
    }
 }
```
    
The method handle() will map to /users/{userId}

URI Template Patterns may contain regex:

```java
@RequestMapping("/{userId:[0-9]+}")
```

URI templates can be captured by handler's method parameter annotated with @PathVariable:

```java
    @RequestMapping("/{userId}")
    public void handle(@PathVariable("userId") String userId) {
            // ...
    }
```

A method without @RequestMapping won't be mapped, even though enclosing class annotations are valid. For example the following code will not map the method handleAllUsersRequest() and will return 404 error for the request /users:

```java
 @Controller
 @RequestMapping("/users")
 public class UserController {

  public String handleAllUsersRequest(){
        .....
  }
 }
```

To fix above mapping (for the request /users):

```java
 @Controller
 @RequestMapping("/users")
 public class UserController {

  @RequestMapping
  public String handleAllUsersRequest(){
        .....
  }
 }
```

@RequestMapping("") is equivalent to @RequestMapping. 
On class level @RequestMapping("") will map to root url "/".

### RequestMethod[] method

The HTTP request methods, this handler can support:

```java
@Controller
@RequestMapping("/users")
public class UserController {

   @RequestMapping(value= "{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
   public String handle(......){
     //..
   }
}
```

Per good design principles, it's better to define separate handler methods based on HTTP methods.

Different handler methods can uniquely be defined based on different HTTP methods (no ambiguity), even if they have same request URL path:

```java
@Controller
@RequestMapping("/users")
public class UserController {

   @RequestMapping(value= "{id}", method = {RequestMethod.GET})
   public String handleGet(.....){
     //..
   }

   @RequestMapping(value= "{id}", method = {RequestMethod.DELETE})
   public String handleDelete(.....){
     //..
   }
}
```

### String[] params

The query string parameters. The annotated method will only be mapped if the query string matches.

This is another level for defining handler methods uniquely (no ambiguity) or in other words to narrow down the primary mapping. In the following example the request /users?id=4 will be mapped to method handleUserId4 and the request /user?id=10 will be mapped to method handleUserId10:

```java
@Controller
@RequestMapping("/users")
public class UserControllerParams {

    @RequestMapping(params = "id=4")
    public String handleUserId4(.....) {
        System.out.println("got param id = 4");
        return "view-name";
    }

    @RequestMapping(params = "id=10")
    public String handleUserId10(....) {
        System.out.println("got param id = 10");
        return "view-name";
    }
}
```

We don't have to necessarily capture the query param using @RequestParam as each method will only be called when params matches, that means we can safely use hardcoded param values inside the handler method.

#### Defining multiple query params:

```java
@Controller
@RequestMapping("/users")
public class UserControllerParams {

    @RequestMapping(params = {"state=TX", "dept=IT"})
    public String handleRequest(.....) {
         ....
        return "view-name";
    }
}
```

We can skip the query param value part. In that case all request having the specified param name will be mapped regardless of their values:

```java
@Controller
@RequestMapping("/users")
public class UserControllerParams {

    @RequestMapping(params = "dept")
    public String handleRequest(.....) {
         ....
        return "view-name";
    }
}
```

### String[] headers

It's just like params element but instead of query params, it is used to specify HTTP headers key-value pair. That uniquely defines handlers (narrowing the primary mapping):

```java
@Controller
@RequestMapping("/users")
public class UserControllerHeader {

    @RequestMapping(headers = "id=4")
    public String handleAllUsersRequest() {
        System.out.println("got header id = 4");
        return "view-name";
    }

    @RequestMapping(headers = "id=10")
    public String handleAllUsersRequest2() {
        System.out.println("got header id = 10");
        return "view-name";
    }
}
```

### String[] consumes

It defines the consumable media types of the mapped request, narrowing the primary mapping.

It's based on Content Negotiation specifications.

```java
@Controller
@RequestMapping("/users")
public class UserControllerConsume {

    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public String handleJson(@RequestBody String s) {
        System.out.println("json body : " + s);
        return "view-name";
    }

    @RequestMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public String handleXML(@RequestBody String s) {
        System.out.println("xml body " + s);
        return "view-name";
    }
}
```

Expressions can be negated by using the "!" operator, as in "!text/plain", which matches all requests with a Content-Type other than "text/plain".

#### What is @RequestBody?

Annotation indicating a method parameter should be bound to the body of the web request. The body of the request is passed through an HttpMessageConverter to resolve the method argument depending on the content type of the request.

### String[] produces

The producible media types of the mapped request, narrowing the primary mapping.

```java
@Controller
@RequestMapping("/users")
public class UserControllerProduce {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
     public  @ResponseBody String handleJson() {
        System.out.println("Got json request");
        return "{ \"userName\": \"Joe\"}";
    }

    @RequestMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public @ResponseBody  String handleXML() {
        System.out.println("Got xml request");
        return "<userName>Joe</userName>";
    }
}
```

Expressions can be negated by using the "!" operator, as in "!text/plain", which matches all requests with a Accept other than "text/plain".

### String[] path

Alias for 'value'

Ant-style path patterns are also supported (e.g. /**/users).

This example will match any URL ending with users e.g. /dept1/dept2/dept3/dept4/dept5/users

```java
@Controller
@RequestMapping("/**/users")
public class UserControllerPath {

    @RequestMapping
    public void handleAllUsersRequest(HttpServletRequest request){
        System.out.println(request.getRequestURL());
    }
}
```

### String name

Assign a name to this mapping.

### Placeholders in path pattern

@RequestMapping's value element can also have a placeholder ${...} pattern against a property source. Check out an example [here](https://www.logicbig.com/how-to/spring-mvc/spring-path-pattern-placeholders.html).