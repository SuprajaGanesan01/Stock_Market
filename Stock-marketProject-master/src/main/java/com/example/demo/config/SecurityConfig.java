package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;

@Configuration
public class SecurityConfig {

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf(csrf -> csrf.disable()) // Disable CSRF for Postman testing
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/api/**").permitAll() // Allow public access to /api/*
	                .anyRequest().authenticated() // Other URLs need authentication (future)
	            )
	            .httpBasic(httpBasic -> httpBasic.disable()) // Disable basic auth
	            .formLogin(form -> form.disable()); // Disable login form

	        return http.build();
	    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
