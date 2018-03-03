# Form Validation using Bean Validation and Spring JSP Form Tag

In our [last example](../02-jsr-349-bean-validation/README.md) we saw how to do field validation declaratively using bean validation API.

We can simplify our code a little more on the view side by using Spring Form JSP Tag Library.

Among many things, Spring Tag Library provides JSP tags to render validation errors implicitly.

Let's modify our last example one more time to see how that works.

## user-registration.jsp

```java
<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<html>
<head>
<style>
span.error {
   color: red;
}
</style>
</head>
<body>

<h3> Registration Form <h3>
<br/>
 <frm:form action="register" method="post" commandName="user">
  <pre>
                  Name <frm:input path="name" />
                       <frm:errors path="name" cssClass="error" />

         Email address <frm:input path="emailAddress" />
                       <frm:errors path="emailAddress" cssClass="error" />

              Password <frm:password path="password" />
                       <frm:errors path="password" cssClass="error" />
                                        <input type="submit" value="Submit" />
  </pre>
 </frm:form>
</body>
</html>
```

In above code, spring form tag is implicitly mapping the backing object fields to the HTML form fields and also taking care of the validation errors via frm:errors.

One more important thing to understand is that, Spring top level form tag must have to specify 'commandName' (or 'modelAttribute', there's no difference between the two) which is actually the name of the 'model attribute' under which the form object is exposed. That also means we are required to use annotation @ModelAttribute on our backing object using the same name as 'commandName'. Please see our controller next.

## The Controller

Comparing with our last example our controller has become simpler.

```java
@Controller
@RequestMapping("/register")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest (Model model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handlePostRequest (@Valid @ModelAttribute("user") User user,
                                     BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-registration";
        }

        userService.saveUser(user);
        return "registration-done";
    }
}
```

## Using @ModelAttribute to bind with Spring form's commandName

Notice, in above handler method we must use @ModelAttribute with a name matching with 'commandName' in our jsp form.