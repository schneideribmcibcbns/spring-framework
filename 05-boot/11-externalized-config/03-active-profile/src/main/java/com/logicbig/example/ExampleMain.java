package com.logicbig.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@SpringBootConfiguration
public class ExampleMain {
    @Bean
    @Profile("in-memory")
    InventoryService inMemoryInventoryService() {
        return new InMemoryInventoryServiceImpl();
    }

    @Bean
    @Profile("production")
    InventoryService productionInventoryService() {
        return new InventoryServiceImpl();
    }

    public static void main(String[] args) throws InterruptedException {
        ApplicationContext context = SpringApplication.run(ExampleMain.class, args);
        InventoryService service = context.getBean(InventoryService.class);
        service.addStockItem("item1", 10, BigDecimal.valueOf(20.5));
    }
}