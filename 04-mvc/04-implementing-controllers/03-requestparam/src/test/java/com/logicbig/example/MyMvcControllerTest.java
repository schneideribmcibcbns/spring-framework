package com.logicbig.example;

import jdk.Exported;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class MyMvcControllerTest {

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
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/employees?dept=IT");
        this.mockMvc.perform(builder)
                    .andExpect(ok);


        builder = MockMvcRequestBuilders.get("/employees?state=NC");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

         builder = MockMvcRequestBuilders.get("/employees?dept=IT&state=NC");
          this.mockMvc.perform(builder)
         .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/employees/234/messages?sendBy=mgr&date=20160210");
        this.mockMvc.perform(builder)
                    .andExpect(ok);


        builder = MockMvcRequestBuilders.get("/employees/234/paystubs?months=5");
        this.mockMvc.perform(builder)
                    .andExpect(ok);


        builder = MockMvcRequestBuilders.get("/employees/234/paystubs?startDate=2000-10-31&endDate=2000-10-31");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/employees/234/report");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/employees/234/report?project=mara");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/employees/234/messages?sendBy=mgr&date=20160210");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

    }
}