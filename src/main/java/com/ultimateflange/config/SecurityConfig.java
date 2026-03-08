package com.ultimateflange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()    // Auth open
                .requestMatchers("/api/products/**").permitAll() // Products open
                .requestMatchers("/api/orders/**").permitAll()   // Orders open
                .anyRequest().permitAll()                         // Sab open
            )
            .httpBasic(basic -> basic.disable())
            .formLogin(login -> login.disable());
        
        return http.build();
    }
}
