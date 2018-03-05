# Creating a custom HttpMessageConverter

This example demonstrates how to create and register a custom HttpMessageConverter.

## Creating the Custom Converter

Following custom converter converts the message body to the Report object. The converter expects that the first line of message body will be the report name and the rest will be the report content.

```java
public class Report {
  private int id;
  private String reportName;
  private String content;
    .............
}
```

```java
public class ReportConverter
      extends AbstractHttpMessageConverter<Report> {

  public ReportConverter() {
      super(new MediaType("text", "report"));
  }

  @Override
  protected boolean supports(Class<?> clazz) {
      return Report.class.isAssignableFrom(clazz);
  }

  @Override
  protected Report readInternal(Class<? extends Report> clazz, HttpInputMessage inputMessage)
          throws IOException, HttpMessageNotReadableException {
      String requestBody = toString(inputMessage.getBody());
      int i = requestBody.indexOf("\n");
      if (i == -1) {
          throw new HttpMessageNotReadableException("No first line found");
      }

      String reportName = requestBody.substring(0, i).trim();
      String content = requestBody.substring(i).trim();

      Report report = new Report();
      report.setReportName(reportName);
      report.setContent(content);
      return report;
  }

  @Override
  protected void writeInternal(Report report, HttpOutputMessage outputMessage)
          throws IOException, HttpMessageNotWritableException {
      try {
          OutputStream outputStream = outputMessage.getBody();
          String body = report.getReportName() + "\n" +
                  report.getContent();
          outputStream.write(body.getBytes());
          outputStream.close();
      } catch (Exception e) {
      }
  }

  private static String toString(InputStream inputStream) {
      Scanner scanner = new Scanner(inputStream, "UTF-8");
      return scanner.useDelimiter("\\A").next();
  }
}
```

## A Controller utilizing the conversion

```java
@Controller
public class ReportController {
  private List<Report> reports = new ArrayList<>();

  @RequestMapping(value = "/reports",
          method = RequestMethod.POST,
          consumes = "text/report")
  @ResponseBody
  public String handleRequest(@RequestBody Report report) {
      report.setId(reports.size() + 1);
      reports.add(report);
      return "report saved: " + report;
  }

  @RequestMapping(
          value = "/reports/{id}",
          method = RequestMethod.GET)
  @ResponseBody
  public Report reportById(@PathVariable("id") int reportId) {
      if (reportId > reports.size()) {
          throw new RuntimeException("No found for the id :" + reportId);
      }
      return reports.get(reportId - 1);
  }
}
```

## Registering the Converter

```java
@EnableWebMvc
@ComponentScan
public class AppConfig extends WebMvcConfigurerAdapter {

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(new ReportConverter());
  }
}
```

## Writing a JUnit test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ReportTests {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;

  @Before
  public void setup() {
      DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
      this.mockMvc = builder.build();
  }

  @Test
  public void testNewReport() throws Exception {
      System.out.println("-- creating new report --");
      MockHttpServletRequestBuilder builder =
              MockMvcRequestBuilders.post("/reports")
                                    .contentType("text/report")
                                    .content(createTestReport());
      mockMvc.perform(builder)
             .andExpect(MockMvcResultMatchers.status()
                                             .isOk())
             .andDo(MockMvcResultHandlers.print());

      System.out.println("\n-- getting report by id --");
      builder = MockMvcRequestBuilders.get("/reports/1");
      mockMvc.perform(builder)
             .andExpect(MockMvcResultMatchers.status()
                                             .isOk())
             .andDo(MockMvcResultHandlers.print());
  }

  private String createTestReport() {
      return "dummy report name\ndummy report content.";
  }
}
```