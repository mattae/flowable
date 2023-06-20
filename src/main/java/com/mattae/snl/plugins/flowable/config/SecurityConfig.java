package com.mattae.snl.plugins.flowable.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .securityMatchers((matchers) -> matchers.requestMatchers("/**"))
                .cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests()
                    .anyRequest().permitAll();
        return http.build();
        //@formatter:on
    }
}