package com.mattae.snl.plugins.flowable.config;

import io.github.jbella.snl.core.api.services.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@ConditionalOnMissingBean(ExtensionService.class)
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
