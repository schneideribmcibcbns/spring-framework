package com.logicbig.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@ComponentScan
public class AppConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .rememberMe()
            .rememberMeCookieName("example-app-remember-me")
            .tokenRepository(persistentTokenRepository())
            .tokenValiditySeconds(24 * 60 * 60);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(appDataSource());
        return repo;
    }

    @Bean
    public DataSource appDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("persistent_login_table.sql")
                .build();
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        builder.inMemoryAuthentication()
               .passwordEncoder(passwordEncoder)
               .withUser("joe")
               .password(passwordEncoder.encode("123"))
               .roles("ADMIN");
    }
}