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