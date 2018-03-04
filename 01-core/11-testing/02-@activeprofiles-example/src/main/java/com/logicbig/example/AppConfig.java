package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

    @Bean
    @Profile(PROFILE_LOCAL)
    public DataService dataServiceLocal() {
        return DataServiceLocal.Instance;
    }

    @Bean
    @Profile(PROFILE_REMOTE)
    public DataService dataServiceRemote() {
        return DataServiceRemote.Instance;
    }

    public static final String PROFILE_LOCAL = "local";
    public static final String PROFILE_REMOTE = "remote";
}