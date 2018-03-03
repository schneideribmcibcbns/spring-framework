package com.logicbig.example;

import com.logicbig.example.service.Dao;
import com.logicbig.example.service.InMemoryCustomerDao;
import com.logicbig.example.service.JpaCustomerDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class DataConfig {

    @Bean
    @Profile(PROFILE_LOCAL)
    public Dao<Customer> inMemoryCustomerDao() {
        return new InMemoryCustomerDao();
    }

    @Bean
    @Profile(PROFILE_DEV)
    public Dao<Customer> jpaDao() {
        return new JpaCustomerDao();

    }

    @Bean
    @Profile(PROFILE_DEV)
    
    public EntityManager devEntityManager() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("objectdb:customerDev.odb");
        return emf.createEntityManager();
    }

    @Bean
    @Profile(PROFILE_PROD)
    public EntityManager prodEntityManager(){
        //todo: get DataSource from JNDI, prepare and return EntityManager
        return null;
    }


    public static final String PROFILE_LOCAL = "local";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_PROD = "prod";
}