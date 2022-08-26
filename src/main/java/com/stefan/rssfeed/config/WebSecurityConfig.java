package com.stefan.rssfeed.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    /**
     * Add security filter to filter chain.
     *
     * @param http http security object
     * @return security filter chain
     * @throws Exception exception.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests().anyRequest().permitAll();
        http.csrf().disable()
                .headers().frameOptions().disable();
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type", "User-Agent", "Access-Control-Request-Method", "Access-Control-Expose-Headers"));

        configuration.setExposedHeaders(List.of("Content-Length", "Transfer-Encoding", "Date", "X-Content-Type-Options", "X-XSS-Protection",
                // Pagination Headers
                "X-Pagination-TotalPages", "X-Pagination-TotalElements", "X-Pagination-Number", "X-Pagination-NumberOfElements", "X-Pagination-Size", "X-Pagination-First", "X-Pagination-Last"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
