# Using HttpEntity

`HttpEntity<T>` is a helper object which encapsulates header and body of an HTTP request or response. It can be used as a handler method parameter.

To get header and body information,the following methods are used:

```java
public HttpHeaders getHeaders()
public T getBody()
```

The usage of HttpEntity is an alternative to using the two parameter: 
* [@RequestHeader HttpHeaders](../../04-implementing-controllers/04-requestheader/README.md), and 

* [@RequestBody String/backing type](../01-http-message-body/README.md)

HttpEntity can be used to return response as well. The additional advantage in this case, when comparing with `@ResponseBody` is, it can include the headers in the response as well.

In the following example we are going to demonstrate the use of HttpEntity with JUnit tests.

## Handling headers and body as String

```java
@RestController
@RequestMapping
public class MyController {

    @RequestMapping
    public void handleRequest (HttpEntity<String> requestEntity) {
        System.out.println("request body: " + requestEntity.getBody());
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println("request headers " + headers);
        HttpEntity<String> responseEntity = new HttpEntity<String>("my response body");
        return responseEntity;
    }
}
```

### The unit test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testUserController () throws Exception {
        MockHttpServletRequestBuilder builder =
                                      MockMvcRequestBuilders.post("/")
                                        .header("testHeader",
                                                "headerValue")
                                        .content("test body");
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
	}
}
```

## Using Backing/Command Object

```java
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String emailAddress;

    //getters and setters
}
```

### The Controller

```java
@RestController
@RequestMapping
public class MyController {

    @RequestMapping("/user")
    public HttpEntity<String> handleUserRequest (HttpEntity<User> requestEntity) {
        User user = requestEntity.getBody();
        System.out.println("request body: " + user);
        System.out.println("request headers " + requestEntity.getHeaders());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));

        HttpEntity<String> responseEntity = new HttpEntity<>("my response body", headers);
        return responseEntity;
    }
}
```

### Unit Test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class ControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testUserController () throws Exception {

        MockHttpServletRequestBuilder builder =
                                    MockMvcRequestBuilders.post("/user")
                                        .header("testHeader",
                                                "headerValue")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createUserInJson("joe",
                                                            "joe@example.com"));
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
    }

    private static String createUserInJson (String name, String email) {
        return "{ \"name\": \"" + name + "\", " +
                            "\"emailAddress\":\"" + email + "\"}";
    }
}
```