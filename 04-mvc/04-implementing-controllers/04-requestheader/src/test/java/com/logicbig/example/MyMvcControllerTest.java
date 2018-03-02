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

       MockHttpServletRequestBuilder builder  = MockMvcRequestBuilders.get("/trades")
                                        .header("User-Agent", "spring test");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

         builder = MockMvcRequestBuilders.get("/trades").header
                            ("From", "user@example.com");
        this.mockMvc.perform(builder).andExpect(ok);

         builder = MockMvcRequestBuilders.get("/trades/tradeCurrencies");
        this.mockMvc.perform(builder)
                    .andExpect(ok);


        builder = MockMvcRequestBuilders.get("/trades").header("User-Agent", "Spring test")
                                        .header("Accept-Language", "fr");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/trades/23")
                                      .header("If-Modified-Since", "Sat, 29  Oct 1994 19:43:31 GMT");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        builder = MockMvcRequestBuilders.get("/trades/exchangeRates");
        this.mockMvc.perform(builder)
                    .andExpect(ok);

        this.mockMvc.perform(builder).andExpect(ok);

    }
}