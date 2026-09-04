package com.security.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.security.auth.security.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {
        http
        /*
         * JWT is stateless
         */
        .csrf(csrf ->
                csrf.disable())
        /*
         * Session disabled
         */
        .sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
        /*
         * Exception handling
         */
        .exceptionHandling(exception ->
                exception.authenticationEntryPoint(
                        authenticationEntryPoint
                ))
        /*
         * API Authorization
         */
        .authorizeHttpRequests(auth -> auth


                /*
                 * Public endpoints
                 */
                .requestMatchers(
                        "/api/auth/register",
                        "/api/auth/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                )
                .permitAll()



                /*
                 * Everything else protected
                 */
                .anyRequest()
                .authenticated()
        )
        /*
         * Authentication Provider
         */
        .authenticationProvider(authenticationProvider)
        /*
         * JWT Filter before username/password filter
         */
        .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );
        return http.build();
    }
}