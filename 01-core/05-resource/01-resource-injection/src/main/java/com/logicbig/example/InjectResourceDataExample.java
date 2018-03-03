package com.logicbig.example;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InjectResourceDataExample {

    public static void main (String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
    }

    @Configuration
    public static class Config {

        @Bean
        public ClientBean clientBean () {
            return new ClientBean();
        }

        //probably we want to use some @Qualifier here
        @Bean
        public String myResourceData (
                            @Value("classpath:myResource.txt") Resource myResource) throws
                            IOException {
            File file = myResource.getFile();
            return new String(Files.readAllBytes(file.toPath()));
        }
    }

    private static class ClientBean {
        @Autowired
        private String myData;

        public void doSomething () throws IOException {
            System.out.println(myData);
        }
    }
}