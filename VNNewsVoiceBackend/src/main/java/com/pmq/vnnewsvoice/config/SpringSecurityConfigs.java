package com.pmq.vnnewsvoice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(
        basePackages = {
                "com.pmq.vnnewsvoice.controller",
                "com.pmq.vnnewsvoice.service",
                "com.pmq.vnnewsvoice.repository",
        }
)
public class SpringSecurityConfigs {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(
                auth -> auth.anyRequest().permitAll()
            )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(httpbasic -> httpbasic.disable());
//                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
