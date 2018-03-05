# Using RequestEntity and ResponseEntity

`org.springframework.http.RequestEntity<T>` extends [HttpEntity](../10-using-httpentity/README.md) and adds additional information of HTTP method and uri to the request.

`org.springframework.http.ResponseEntity<T>` also extends `HttpEntity`, where we can add additional `HttpStatus` (see also @ResponseStatus) to the response.

In this example we are going to show the use of RequestEntity and RequestResponse with JUnit tests.

## Handling request having String body

```java
@Controller
@RequestMapping
public class MyController {

    @RequestMapping("test")
    public ResponseEntity<String> handleRequest (RequestEntity<String> requestEntity) {
        System.out.println("request body : " + requestEntity.getBody());
        HttpHeaders headers = requestEntity.getHeaders();
        System.out.println("request headers : " + headers);
        HttpMethod method = requestEntity.getMethod();
        System.out.println("request method : " + method);
        System.out.println("request url: " + requestEntity.getUrl());

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                                                                     HttpStatus.OK);
        return responseEntity;
    }
}
```

### The unit test

```
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
                                      MockMvcRequestBuilders.post("/test")
                                        .header("testHeader",
                                                "headerValue")
                                        .content("test body");
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
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
@Controller
@RequestMapping
public class MyController {

   @RequestMapping("/user")
    public ResponseEntity<String> handleUserRequest (RequestEntity<User> requestEntity) {
        User user = requestEntity.getBody();
        System.out.println("request body: " + user);
        System.out.println("request headers " + requestEntity.getHeaders());
        System.out.println("request method : " + requestEntity.getMethod());

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("Cache-Control", Arrays.asList("max-age=3600"));

        ResponseEntity<String> responseEntity = new ResponseEntity<>("my response body",
                                                                     headers,
                                                                     HttpStatus.OK);
        return responseEntity;
    }
}


Unit Test
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