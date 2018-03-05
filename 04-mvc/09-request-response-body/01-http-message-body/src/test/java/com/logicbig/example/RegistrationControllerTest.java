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
public class RegistrationControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup () {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testController () throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                                                .isOk();

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/message")
                                                                      .contentType(MediaType.TEXT_PLAIN)
                                                                      .content("test message");
        this.mockMvc.perform(builder)
                    .andExpect(ok)
                    .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void testFormPostController () throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                                                .isOk();

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.post("/message")
                                                  .contentType("application/x-www-form-urlencoded")
                                                  .accept(MediaType.TEXT_PLAIN)
                                                  //use param instead of content
                                                  .param("name", "joe");

        this.mockMvc.perform(builder)
                    .andExpect(ok);


    }

    @Test
    public void testPostFormPostController () throws Exception {
        ResultMatcher created = MockMvcResultMatchers.status()
                                                .isCreated();

        MockHttpServletRequestBuilder builder =
                            MockMvcRequestBuilders.put("/message")
                                                  .contentType("application/x-www-form-urlencoded")
                                                  .accept(MediaType.TEXT_PLAIN)
                                                  .content("name=mike");
        this.mockMvc.perform(builder)
                    .andExpect(created);


    }
}