package com.logicbig.example;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:prod.properties")
@Configuration
@ComponentScan
public class AppConfig {
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        ReportService rs = context.getBean(ReportService.class);
        System.out.println(rs.getReportSubscriber());
    }
}