# HttpMessageConverter for YAML conversion

In the last tutorial, we got familiar with creating a custom `HTTPMessageConverter`. In this tutorial, we will create another one. This `HTTPMessageConverter` will convert request body containing YAML content to user defined object and vice-versa. We are going to use `SnakeYAML`, which is a Java based processor for parsing YAML data to/from Java Objects.

## Creating the Controller

```java
@Controller
public class ExampleController {
  
  @RequestMapping(
            value = "/newEmployee",
            consumes = "text/yaml",
            produces = MediaType.TEXT_PLAIN_VALUE,
            method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public String handleRequest (@RequestBody Employee employee) {
      System.out.printf("In handleRequest method, employee: %s%n", employee);
      String s = String.format("Employee saved: " + employee.getName());
      System.out.println(s);
      return s;
  }
  
  @RequestMapping(
            value = "/employee",
            produces = "text/yaml",
            method = RequestMethod.GET)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public Employee handleRequest2 (@RequestParam String id) {
      //create a test employee
      return new Employee(id, "Tina", "111-111-1111");
  }
}
```

```java
public class Employee {
  private String id;
  private String name;
  private String phoneNumber;
    .............
}
```


## Creating the Converter

```java
public class YamlHttpMessageConverter<T>
        extends AbstractHttpMessageConverter<T> {
  
  public YamlHttpMessageConverter () {
      super(new MediaType("text", "yaml"));
  }
  
  @Override
  protected boolean supports (Class<?> clazz) {
      return true;
  }
  
  @Override
  protected T readInternal (Class<? extends T> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
      Yaml yaml = new Yaml();
      T t = yaml.loadAs(inputMessage.getBody(), clazz);
      return t;
  }
  
  @Override
  protected void writeInternal (T t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
      Yaml yaml = new Yaml();
      OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody());
      yaml.dump(t, writer);
      writer.close();
  }
}
```

## Registering the Converter

```java
@EnableWebMvc
@ComponentScan("com.logicbig.example")
public class AppConfig extends WebMvcConfigurerAdapter {
  
  @Override
  public void extendMessageConverters (List<HttpMessageConverter<?>> converters) {
      converters.add(new YamlHttpMessageConverter<>());
  }
}
```

## Testing with JUnit tests

### Testing request

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ControllerTest {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup () {
      DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
      this.mockMvc = builder.build();
  }
    .............
  @Test
  public void testConsumerController () throws Exception {
      MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/newEmployee")
                                      .contentType("text/yaml")
                                      .accept(MediaType.TEXT_PLAIN_VALUE)
                                      .content(getNewEmployeeInYaml());
      this.mockMvc.perform(builder)
                  .andExpect(MockMvcResultMatchers.status()
                                                  .isOk())
                  .andExpect(MockMvcResultMatchers.content()
                                                  .string("Employee saved: Tina"))
                  .andDo(MockMvcResultHandlers.print());
      ;
  }
    .............
  public String getNewEmployeeInYaml () {
      return "id: 1\nname: Tina\nphoneNumber: 111-111-1111\n";
  }
}
```