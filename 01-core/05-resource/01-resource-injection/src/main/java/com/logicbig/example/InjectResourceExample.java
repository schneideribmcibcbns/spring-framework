package com.logicbig.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class InjectResourceExample {

    public static void main (String[] args) throws IOException {
        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext(
                                                Config.class);
        ClientBean bean = context.getBean(ClientBean.class);
        bean.doSomething();
        ;
    }

    @Configuration
    public static class Config {

        @Bean
        public ClientBean clientBean () {
            return new ClientBean();
        }

    }

    private static class ClientBean {
        @Value("classpath:myResource.txt")
        private Resource myResource;

        public void doSomething () throws IOException {
            File file = myResource.getFile();
            String s = new String(Files.readAllBytes(file.toPath()));
            System.out.println(s);
        }
    }
}