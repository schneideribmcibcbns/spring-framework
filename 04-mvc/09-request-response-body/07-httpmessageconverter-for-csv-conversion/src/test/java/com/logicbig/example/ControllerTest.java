package com.logicbig.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
    
    @Test
    public void testProducerController () throws Exception {
        MockHttpServletRequestBuilder builder =
                  MockMvcRequestBuilders.get("/employeeList")
                                        .accept("text/csv");
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
    }
    
    public String getNewEmployeeListInCsv () {
        return "id, name, phoneNumber\n1,Joe,123-212-3233\n2,Sara,132,232,3111\n" +
                  "3,Mike,111-222-3333\n";
    }
}