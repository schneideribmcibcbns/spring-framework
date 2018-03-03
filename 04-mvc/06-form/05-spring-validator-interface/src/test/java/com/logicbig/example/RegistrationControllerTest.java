package com.logicbig.example;

import org.junit.Before;
import org.junit.Ignore;
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
    public void testUserController () throws Exception {
        ResultMatcher ok = MockMvcResultMatchers.status()
                                                .isOk();
        ResultMatcher registerView = MockMvcResultMatchers.view()
                                                          .name("user-registration");
        ResultMatcher doneView = MockMvcResultMatchers.view()
                                                      .name("registration-done");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/register");
        this.mockMvc.perform(builder)
                    .andExpect(ok)
                    .andExpect(registerView);

        builder = MockMvcRequestBuilders.post("/register")
                                        .param("name", "KatieK")
                                        .param("emailAddress", "kat@exmaple.com")
                                        .param("password", "abcadf");
        this.mockMvc.perform(builder)
                    .andExpect(ok)
                    .andExpect(doneView);

    }
}