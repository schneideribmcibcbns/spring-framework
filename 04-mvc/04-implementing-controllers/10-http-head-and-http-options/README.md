# Built-in Support for HTTP HEAD and HTTP OPTIONS

These features are only available starting Spring 4.3.

## Implicit HEAD support

From Spring's MVC doc:

@RequestMapping methods mapped to "GET" are also implicitly mapped to "HEAD", i.e. there is no need to have "HEAD" explicitly declared. An HTTP HEAD request is processed as if it were an HTTP GET except instead of writing the body only the number of bytes are counted and the "Content-Length" header set.

That means we never have to separately create a handler method for HTTP HEAD verb as spring implicitly supports that, given that GET verb is already defined for the target URL.

## Example

### The Controller

Let's create a very simple controller with a handler method populating some headers:

```java
@Controller
public class MyController {
    Logger logger = Logger.getLogger(MyController.class.getSimpleName());

    @RequestMapping(value = "test", method = {RequestMethod.GET})
    public HttpEntity<String> handleTestRequest () {

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put("test-header", Arrays.asList("test-header-value"));

        HttpEntity<String> responseEntity = new HttpEntity<>("test body", headers);

        logger.info("handler finished");
        return responseEntity;
    }
} 
```

### JUnit Tests

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
    public void testGet () throws Exception {

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.get("/test");

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testHead () throws Exception {

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.head("/test");

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
    }
}
```

### Output

Running testGet() gives output:

```shell
.........

Jul 31, 2016 2:40:34 PM com.logicbig.example.MyController handleTestRequest
INFO: handler finished

MockHttpServletRequest:
      HTTP Method = GET
      Request URI = /test
       Parameters = {}
          Headers = {}

Handler:
             Type = com.logicbig.example.MyController
           Method = public org.springframework.http.HttpEntity<java.lang.String> com.logicbig.example.MyController.handleTestRequest()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = {test-header=[test-header-value], Content-Type=[text/plain;charset=ISO-8859-1], Content-Length=[9]}
     Content type = text/plain;charset=ISO-8859-1
             Body = test body
    Forwarded URL = null
   Redirected URL = null
          Cookies = []

Process finished with exit code 0
```

Running testHead() gives output:

```shell
Jul 31, 2016 2:44:06 PM com.logicbig.example.MyController handleTestRequest
INFO: handler finished

MockHttpServletRequest:
      HTTP Method = HEAD
      Request URI = /test
       Parameters = {}
          Headers = {}

Handler:
             Type = com.logicbig.example.MyController
           Method = public org.springframework.http.HttpEntity<java.lang.String> com.logicbig.example.MyController.handleTestRequest()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = {test-header=[test-header-value], Content-Type=[text/plain;charset=ISO-8859-1], Content-Length=[9]}
     Content type = text/plain;charset=ISO-8859-1
             Body =
    Forwarded URL = null
   Redirected URL = null
          Cookies = []

Process finished with exit code 0
```

Note that this time no body content printed, just headers. Also the handler method is still executed as seen in log 'INFO: handler finished' at beginning.

## Implicit OPTIONS support

From Spring's MVC doc:

@RequestMapping methods have built-in support for HTTP OPTIONS. By default an HTTP OPTIONS request is handled by setting the "Allow" response header to the HTTP methods explicitly declared on all @RequestMapping methods with matching URL patterns. When no HTTP methods are explicitly declared the "Allow" header is set to "GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS"

That means, we never have to separately create a handler method for HTTP OPTIONS verb as spring implicitly supports that, given that all handler methods explicitly specify the HTTP method with each @RequestMapping for the target URL.

### Example

Let's continue with the above example just add one more test for the HTTP OPTIONS verb:

```java
@Test
public void testOptions () throws Exception {

    ResultMatcher accessHeader = MockMvcResultMatchers.header()
                                                      .string("Allow", "GET,HEAD");

    MockHttpServletRequestBuilder builder =
                        MockMvcRequestBuilders.options("/test");

    this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                                                .isOk())
                .andExpect(accessHeader)
                .andDo(MockMvcResultHandlers.print());
}
```

### Output

```shell
MockHttpServletRequest:
      HTTP Method = OPTIONS
      Request URI = /test
       Parameters = {}
          Headers = {}

Handler:
             Type = org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping$HttpOptionsHandler
           Method = public org.springframework.http.HttpHeaders org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping$HttpOptionsHandler.handle()

Async:
    Async started = false
     Async result = null

Resolved Exception:
             Type = null

ModelAndView:
        View name = null
             View = null
            Model = null

FlashMap:
       Attributes = null

MockHttpServletResponse:
           Status = 200
    Error message = null
          Headers = {Allow=[GET,HEAD]}
     Content type = null
             Body =
    Forwarded URL = null
   Redirected URL = null
          Cookies = []

Process finished with exit code 0
```

Note the 'Allow' header. There's an extra HEAD option because of Spring implicit support of HEAD for each GET method.

Also notice, no body is printed again, additionally even the handler method is not get called this time as no 'INFO: handler finished' printed.