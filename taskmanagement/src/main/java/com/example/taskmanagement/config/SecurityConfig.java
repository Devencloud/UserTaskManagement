package com.example.taskmanagement.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.taskmanagement.components.CustomAccessDeniedHandler;
import com.example.taskmanagement.security.JwtAuthenticationFilter;
import com.example.taskmanagement.security.JwtUtil;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

     private final CustomAccessDeniedHandler customAccessDeniedHandler;
    
     private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil,CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtUtil = jwtUtil;
        this.customAccessDeniedHandler =customAccessDeniedHandler;
    }
       
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
               .csrf(csrf -> csrf.disable()) // Disable CSRF for API
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // No sessions
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/login").permitAll() // Open login endpoint
                .requestMatchers("/api/users/**").hasRole("ADMIN") // Restrict /api/users to ADMIN
                .requestMatchers("/api/tasks/**").authenticated() // Require auth for /api/tasks
                .anyRequest().authenticated() // All other endpoints require auth
            )
             .exceptionHandling(ex -> ex
            .accessDeniedHandler(customAccessDeniedHandler) 
        )
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
}






































































