# Providing a Model attribute value to a handler method by using @ModelAttribute

In the last tutorial we saw the primary purpose of @ModelAttribute i.e. if annotated on method level, the Model object is populated with common attributes before a handler method is invoked. Here we are going to see one more use of this annotation.

# Using @ModelAttribute with a method parameter

@ModelAttribute annotation on a handler method parameter indicates the parameter's value should be retrieved from the Model map.

There are two main steps to accomplish that.

First step (as we saw in the last tutorial) is to annotate a method with @ModelAttribute and return an object of any type from that method.:

```java
@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getUser (@PathVariable("userId") long userId) {
        return userService.getUserById(userId);
    } 
}
```

Second step is to create a handler method and add an extra parameter of the same type returned in the first step. Also annotate the parameter with @ModelAttribute indicating its name.

```java
@Controller
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;


    @RequestMapping("{userId}")
    public String handleRequestById (@ModelAttribute("user") User user, Model model) {
        model.addAttribute("msg", "user  : " + user);
        return user!=null && "admin".equals(user.getRole()) ? "admin-page" : "user-page";
    }

    @ModelAttribute("user")
    public User getUser (@PathVariable("userId") long userId) {
        return userService.getUserById(userId);
    }
}
```

## How it works?

Before invoking any handler methods annotated with @RequestMapping, Spring calls all @ModelAttribute methods.

All return values of @ModelAttribute methods are populated in the Model object.

Spring then moves to next step and prepares to call the target handler method (the one which matches the request). On finding @ModelAttribute in the target handler method parameters, Spring tries to find the named object in the Model map. In our example it looks by the name 'user'.

On finding the matching named object, Spring populates the handler parameter with its value and calls the handler.

Also, if there's @SessionAttributes annotation on the controller class, then Spring first checks whether the named value for the handler's @ModelAttribute parameter exists in the HttpSession, if yes, then it will be used rather than invoking the related method with @ModelAttribute. See next tutorial for details.

If there's no match found then Spring attempts to instantiate the object using it's default constructor, then assign to the parameter and call the handler.

## When to use @ModelAttribute as handler parameter?

The object returned by @ModelAttribute annotated method, will already be in the Model object, which is accessible in the view. So why we need to access that object in our handler method? The answer is whenever we want to conveniently apply some logic in the handler, based on that object. Like in our above example: based on User#role we are selecting different view name.
