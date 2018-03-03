package com.logicbig.example;

import com.logicbig.example.service.CustomerService;
import com.logicbig.example.ui.CustomerUi;
import com.logicbig.example.ui.JavaFxCustomerUi;
import com.logicbig.example.ui.SwingCustomerUi;
import org.springframework.context.annotation.*;



@Configuration
@Import(DataConfig.class)
public class AppConfig {

    @Bean
    @Profile(PROFILE_SWING)
    public CustomerUi swingCustomerUi () {
        return new SwingCustomerUi();
    }

    @Bean
    @Profile(PROFILE_JAVA_FX)
    public CustomerUi javaFxCustomerUi () {
        return new JavaFxCustomerUi();
    }

    @Bean
    public CustomerService customerService () {
        return new CustomerService();
    }

    public static void main (String[] args) {

        AnnotationConfigApplicationContext context =
                            new AnnotationConfigApplicationContext();
        context.getEnvironment()
               .setActiveProfiles(PROFILE_JAVA_FX, DataConfig.PROFILE_LOCAL);
        context.register(AppConfig.class);
        context.refresh();

        CustomerUi bean = context.getBean(CustomerUi.class);
        bean.showUi();
    }

    public static final String PROFILE_SWING = "swing";
    public static final String PROFILE_JAVA_FX = "javafx";
}