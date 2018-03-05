# Building RESTful Web Service using @RestController

It's a very common use case to have Controllers implement a REST API, thus serving only JSON, XML or custom MediaType content. For convenience, instead of annotating all your `@RequestMapping` methods with `@ResponseBody`, you can annotate your Controller Class with `@RestController`. `@RestController` is a stereotype annotation that combines `@ResponseBody` and `@Controller`. More than that, it gives more meaning to your Controller and also may carry additional semantics in future releases of the framework.

The annotation `@RestController` is meta annotated with `@Controller` and `@ResponseBody`, that means we don't have to explicitly annotate our handler methods with `@ResponseBody`, but we still have to use `@RequestBody` in our handler method parameters.

## Create Backing Object

```java
public class User implements Serializable {
    private Long id;
    private String name;
    private String password;
    private String emailAddress;

    //getters and setters
}
```

## Create Controller

```java
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "register", method = RequestMethod.POST,
                        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void handleJsonPostRequest (@RequestBody User user, Model model) {
        System.out.println("saving user: "+user);
        userService.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.GET,
                        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<User> handleAllUserRequest () {
        return userService.getAllUsers();
    }
}
```

## Unit Test

Using Spring MVC Test Framework

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
                            MockMvcRequestBuilders.post("/users/register")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(createUserInJson("joe",
                                                                      "joe@example.com",
                                                                      "abc"));

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isCreated());

       // create one more user
        builder = MockMvcRequestBuilders.post("/users/register")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(createUserInJson("mike",
                                                                  "mike@example.com",
                                                                  "123"));

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isCreated());

        // get all users
        builder = MockMvcRequestBuilders.get("/users")
                                        .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());

    }

    private static String createUserInJson (String name, String email, String password) {
        return "{ \"name\": \"" + name + "\", " +
                            "\"emailAddress\":\"" + email + "\"," +
                            "\"password\":\"" + password + "\"}";
    }
}
```