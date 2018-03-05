# HTTP Message Body handling using @RequestBody and @ResponseBody

Spring provides a generic mechanism of converting HTTP Message body to/from Java objects.

Based on 'Content-Type' and 'Accept' of request header values, a handler method is first mapped.

If the handler method has `@RequestBody` in it's parameter list and/or `@ResponseBody` as it's return type, then a suitable implementation of `HttpMessageConverter` is selected depending on media type of request or response.

---

**What is HttpMessageConverter?**

`HttpMessageConverter` is a strategy interface that can convert HTTP request body to an object type or the an object type to HTTP response body.

---

By default Spring supports various `HttpMessageConverter` and more can be discovered if they are in class path. In this tutorial we are going to show examples of the followings:

* ByteArrayHttpMessageConverter converts byte arrays.

* StringHttpMessageConverter converts strings.

* FormHttpMessageConverter converts form data to/from a MultiValueMap<String, String>

## Byte Array and String conversions

```java
@Controller
@RequestMapping("message")
public class MyController {

    @RequestMapping(consumes = MediaType.TEXT_PLAIN_VALUE,
                    produces = MediaType.TEXT_HTML_VALUE,
                    method = RequestMethod.GET)
    @ResponseBody
    public String handleRequest (@RequestBody byte[] bytes, @RequestBody String str) {

        System.out.println("body in bytes: "+bytes);
        System.out.println("body in string: "+str);

        return "<html><body><h1>Hi there</h1></body></html>";
    }
}
```

In this example, `ByteArrayHttpMessageConverter` is used to convert the request body into the byte array method parameter `byte` and `StringHttpMessageConverter` is used to convert the request body into String parameter `str`.

## Form POST data conversion

The content-type of form data is `application/x-www-form-urlencoded`. `FormHttpMessageConverter` is used for this content type. However, it can only bind the request/response body with a `MultiValueMap`, and cannot bind to backing objects. To bind the form data to backing object, we should always use either Spring implicit `DataBinder` approach or `@ModelAttribute`. 

```java
@Controller
@RequestMapping("message")
public class MyController {

    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void handleFormRequest(@RequestBody MultiValueMap<String, String> formParams) {
    System.out.println("form params received " + formParams);
    }
}
```

---

**What is @ResponseStatus?**

> Marks a method or exception class with the status code() and reason() that should be returned. The status code is applied to the HTTP response when the handler method is invoked and overrides status information set by other means, like ResponseEntity or "redirect:".

---

## Form PUT data conversion

```java
@Controller
@RequestMapping("message")
public class MyController {

   @RequestMapping(method = RequestMethod.PUT,
                    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
   @ResponseStatus(HttpStatus.CREATED)
   public void handleFormPutRequest(@RequestBody MultiValueMap<String, String> formParams){
        System.out.println("form params received " + formParams);
   }
}
```

## What's Next?

In next tutorials we are going to demonstrate how to use `@RequestBody` and `@ResponseBody` to convert request/response body data to backing objects. The commonly used data types are XML and JSON. We can always write our own `HttpMessageConverter` for other data types. Also note that any data type can always be mapped to String or byte[].