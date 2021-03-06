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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebConfigRegex.class)
public class UserControllerTestRegex {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testMyMvcController() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        ResultMatcher userId = MockMvcResultMatchers.model().attribute("userId", "243");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users/243");
        this.mockMvc.perform(builder)
                  .andExpect(ok)
                  .andExpect(userId);
    }

    @Test
    public void testMyMvcController2() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        ResultMatcher userId = MockMvcResultMatchers.model().attribute("userStringId", "abc");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users/abc");
        this.mockMvc.perform(builder)
                  .andExpect(ok)
                  .andExpect(userId);
    }

    @Test
    public void testMyMvcController3() throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status().is(404);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users/3abc");
        this.mockMvc.perform(builder)
                  .andExpect(ok);
    }
}