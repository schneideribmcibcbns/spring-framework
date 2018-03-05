# HttpMessageConverter for CSV conversion

In this tutorial, we will learn how to create and register a custom HttpMessageConverter. The converter will convert the request csv body data to user defined object. The converter will also convert the response user object to csv data. We are going to use OpenCSV parser library for Java object to CSV (and vice-versa) conversion.

## Converting request body CSV to Java object list

### Creating the Controller

```java
@Controller
public class ExampleController {

  @RequestMapping(
            value = "/newEmployee",
            consumes = "text/csv",
            produces = MediaType.TEXT_PLAIN_VALUE,
            method = RequestMethod.POST)
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  public String handleRequest (@RequestBody EmployeeList employeeList) {
      System.out.printf("In handleRequest method, employeeList: %s%n", employeeList.getList());
      String s = String.format("size: " + employeeList.getList().size());
      System.out.println(s);
      return s;
  }
    .............
}
```

```java
public class EmployeeList extends ListParam<Employee> {
}

```java
public class ListParam<T> {

  private List<T> list;

  public List<T> getList () {
      return list;
  }
  
  public void setList (List<T> list) {
      this.list = list;
  }

}
```

```java
package com.logicbig.example;

import com.opencsv.bean.CsvBindByName;

public class Employee {
  @CsvBindByName
  private String id;
  @CsvBindByName
  private String name;
  @CsvBindByName
  private String phoneNumber;
    .............
}
```

### Creating the Converter

```java
package com.logicbig.example;


import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CsvHttpMessageConverter<T, L extends ListParam<T>>
        extends AbstractHttpMessageConverter<L> {
  
  public CsvHttpMessageConverter () {
      super(new MediaType("text", "csv"));
  }
  
  @Override
  protected boolean supports (Class<?> clazz) {
      return ListParam.class.isAssignableFrom(clazz);
  }
  
  @Override
  protected L readInternal (Class<? extends L> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
      HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
      Class<T> t = toBeanType(clazz.getGenericSuperclass());
      strategy.setType(t);
      
      CSVReader csv = new CSVReader(new InputStreamReader(inputMessage.getBody()));
      CsvToBean<T> csvToBean = new CsvToBean<>();
      List<T> beanList = csvToBean.parse(strategy, csv);
      try {
          L l = clazz.newInstance();
          l.setList(beanList);
          return l;
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
  
  @SuppressWarnings("unchecked")
  @Override
  protected void writeInternal (L l, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
      
      HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
      strategy.setType(toBeanType(l.getClass().getGenericSuperclass()));
      
      OutputStreamWriter outputStream = new OutputStreamWriter(outputMessage.getBody());
      StatefulBeanToCsv<T> beanToCsv =
                new StatefulBeanToCsvBuilder(outputStream)
                          .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                          .withMappingStrategy(strategy)
                          .build();
      try {
          beanToCsv.write(l.getList());
          outputStream.close();
      } catch (Exception e) {
          throw new RuntimeException(e);
      }
  }
  
  @SuppressWarnings("unchecked")
  private Class<T> toBeanType (Type type) {
      return (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
  }
}
```

### Registering the Converter

```java
@EnableWebMvc
@ComponentScan("com.logicbig.example")
public class AppConfig extends WebMvcConfigurerAdapter {
 
  
  @Override
  public void extendMessageConverters (List<HttpMessageConverter<?>> converters) {
      converters.add(new CsvHttpMessageConverter<>());
  }
}
```

## Writing JUnit test

```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = AppConfig.class)
public class ControllerTest {
  @Autowired
  private WebApplicationContext wac;
  private MockMvc mockMvc;
    .............
  @Test
  public void testConsumerController () throws Exception {
      MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post("/newEmployee")
                                      .contentType("text/csv")
                                      .accept(MediaType.TEXT_PLAIN_VALUE)
                                      .content(getNewEmployeeListInCsv());
      this.mockMvc.perform(builder)
                  .andExpect(MockMvcResultMatchers.status()
                                                  .isOk())
                  .andExpect(MockMvcResultMatchers.content()
                                                  .string("size: 3"))
                  .andDo(MockMvcResultHandlers.print());
      ;
  }
    .............
  public String getNewEmployeeListInCsv () {
      return "id, name, phoneNumber\n1,Joe,123-212-3233\n2,Sara,132,232,3111\n" +
                "3,Mike,111-222-3333\n";
  }
}
```