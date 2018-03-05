# Prevent duplicate form submission

As we mentioned here, redirect is a useful trick to avoid submitting same form multiple times. As a result of URL redirection client receives the result page with a different HTTP request, ideally with a GET method. The browser address bar shows a different URL instead the original form URL. If user refreshes the same page multiple times he/she will not issue another post submission.

In this complete example of 'user registration' we are going to demonstrate how we can achieve that.

## Create the backing/command object

```
public class User implements Serializable{
    private Long id;

    @Size(min = 5, max = 20)
    private String name;


    @Size(min = 6, max = 15)
    @Pattern(regexp = "\\S+", message = "Spaces are not allowed")
    private String password;

    @NotEmpty
    @Email
    private String emailAddress;

    //getters and setters
}
```

In above backing object we used JSR 349 constraints annotations.

## The Controller

```
@Controller
@RequestMapping
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    //shows the empty form with get request
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String handleGetRequest (Model model) {
        model.addAttribute("user", new User());
        return "user-registration";
    }

    //form post handler which redirect to other URL with GET method
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String handlePostRequest (@Valid @ModelAttribute("user") User user,
                                     BindingResult bindingResult,
                                     RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            return "user-registration";
        }

        userService.saveUser(user);
        ra.addFlashAttribute("user", user);
        return "redirect:/registration-success";
    }

    //redirected handler
    @RequestMapping(value = "registration-success", method = RequestMethod.GET)
    public String handleRegistrationDone(@ModelAttribute("user") User user){
        System.out.println("user....: "+user);
        return "registration-done";
    }
}
```

In above example we used `RedirectAttributes#addFlashAttribute`. Please see [last example](../05-redirectattributes/README.md) for more details about that.

## user-registration.jsp

```
<%@taglib uri="http://www.springframework.org/tags/form" prefix="frm"%>
<html>
<head>
<style>
.error {
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

## registration-done.jsp

```
<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<body>
<h3> Registration done </h3>
<p>User Name: ${user.name}</p>
<p>User email: ${user.emailAddress}
</body>
</html>
```

