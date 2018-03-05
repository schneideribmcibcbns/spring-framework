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
    
    @Test
    public void testProducerController () throws Exception {
        MockHttpServletRequestBuilder builder =
                  MockMvcRequestBuilders.get("/employee")
                                        .accept("text/yaml")
                                        .param("id", "1");
        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk())
                    .andDo(MockMvcResultHandlers.print());
    }
    
    public String getNewEmployeeInYaml () {
        return "id: 1\nname: Tina\nphoneNumber: 111-111-1111\n";
    }
}