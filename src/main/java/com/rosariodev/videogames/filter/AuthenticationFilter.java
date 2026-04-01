package com.rosariodev.videogames.filter;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rosariodev.videogames.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class AuthenticationFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter(customUserDetailsService);
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
       
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }


    @SuppressWarnings("removal")
    @Bean
public DefaultSecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http.csrf().disable()
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/public/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/products").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/products/*").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/products/search").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/products/reviews").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/products/review/*").authenticated() 
            .requestMatchers(HttpMethod.GET, "/api/orders").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/add-chart").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/chart").authenticated()
            .requestMatchers(HttpMethod.DELETE, "/api/remove-product-chart").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/checkout").authenticated()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().permitAll()
        )
        .sessionManagement(session ->
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    
        return http.build();
    }

    

}
