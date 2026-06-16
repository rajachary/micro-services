package com.cloud.gatewayserver.config;

import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.web.reactive.*;
import org.springframework.security.config.web.server.*;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.*;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
                .authorizeExchange(ex ->
                        ex.pathMatchers("/accounts/**").authenticated()
                            .pathMatchers("/events/**").permitAll()
                                .pathMatchers("/h2-console/**").permitAll())
                .oauth2ResourceServer( oAuth2ResourceServerSpec ->
                        oAuth2ResourceServerSpec
                            .jwt(Customizer.withDefaults()))
                .csrf( csrfSpec ->
                        csrfSpec.disable())
                .build();
    }
    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withJwkSetUri("http://localhost:7080/realms/master").build();
    }
}
