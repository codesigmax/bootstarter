package com.qfleaf.bootstarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfleaf.bootstarter.security.handler.UnifiedAccessDeniedHandler;
import com.qfleaf.bootstarter.security.handler.UnifiedAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, ObjectMapper objectMapper) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(// Knife4j 相关路径
                                        "/doc.html/**",
                                        "/webjars/**",
                                        "/swagger-resources/**",
                                        "/v2/api-docs/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html/**",
                                        "/favicon.ico").permitAll()
                                .anyRequest().permitAll()
//                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .exceptionHandling(
                        config -> {
                            config.authenticationEntryPoint(new UnifiedAuthenticationEntryPoint(objectMapper));
                            config.accessDeniedHandler(new UnifiedAccessDeniedHandler(objectMapper));
                        }
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, List<AuthenticationProvider> providers) throws Exception {
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);
        for (AuthenticationProvider provider : providers) {
            sharedObject.authenticationProvider(provider);
        }
        return sharedObject.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
