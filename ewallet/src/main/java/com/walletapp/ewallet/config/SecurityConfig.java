package com.walletapp.ewallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    //this adds the basic authentication filter to the application instead of the default form login filter
    @Bean
    public  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.httpBasic(withDefaults());
    return http.build();
    }
}
