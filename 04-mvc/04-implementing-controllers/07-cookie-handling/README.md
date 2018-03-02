# Cookie handling

## Reading cookies using @CookieValue

Annotation @CookieValue allows a handler method parameter to be mapped to the value of an Http Cookie:

```java
@RequestMapping
public String handleRequest(
                    @CookieValue(value = "myCookieName",
                                        defaultValue = "defaultCookieValue")
                    String cookieValue, Model model) {
    System.out.println(cookieValue);
    ......
    return "my-page";
```
}
Like other scenarios, there's an automatic value type conversion. In above example the cookie value is mapped to String type.

## Writing Cookies using HttpServletResponse

To write cookies we can use javax.servlet.http.HttpServletResponse:

```java
@RequestMapping
public String handleRequest(HttpServletResponse response, Model model) {

    Cookie newCookie = new Cookie("testCookie", "testCookieValue");
    newCookie.setMaxAge(24 * 60 * 60);
    response.addCookie(newCookie);
    .......
    return "my-page";
}
```

## Using HttpServletRequest to read Cookies:

Instead of using @CookieValue we can also use HttpServletRequest as handler parameter to iterate through or read cookie values.

```java
 @RequestMapping
    public String handleTestRequest (Model model,
                                     HttpServletRequest request){


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Arrays.stream(cookies)
                  .forEach(c -> System.out.println(c.getName() + "=" + c.getValue()));
        }
        .......
        return "my-page";
    }
```

In above example we can also use the static helper method to find a specific cookie: org.springframework.web.util.WebUtils#getCookie(HttpServletRequest request, String name) 