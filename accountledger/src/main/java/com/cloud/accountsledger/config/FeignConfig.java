package com.cloud.accountsledger.config;

import feign.*;
import org.springframework.cloud.openfeign.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.web.reactive.*;
import org.springframework.security.web.*;
import org.springframework.stereotype.*;


@Configuration
@EnableWebSecurity
public class FeignConfig {
    @Bean
    public FeignLoggerFactory feignLoggerFactory() {
        return new FeignLoggerFactory() {
            @Override
            public Logger create(Class<?> type) {
                return new Logger.JavaLogger(type); // or SLF4JLogger
            }
        };
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // Allow public access to the H2 console path
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                // Disable CSRF protection specifically for the console
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
//                // Allow the database console UI to load inside browser frames
//                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
//
//        return http.build();
//    }
}
