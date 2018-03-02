package com.logicbig.example;

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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("GetMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.get("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }


    
    @Test
    public void testPost () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PostMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.post("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }



    
    @Test
    public void testPut () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PutMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.put("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }


    
    @Test
    public void testDelete () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("DeleteMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.delete("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }


    
    @Test
    public void testPatch () throws Exception {

        ResultMatcher viewMatcher = MockMvcResultMatchers.view()
                                                         .name("PatchMapping-view");

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.patch("/test");


        this.mockMvc.perform(builder)
                    .andExpect(viewMatcher)
                    .andExpect(MockMvcResultMatchers.status()
                                                    .isOk());
    }

}