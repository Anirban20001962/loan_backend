package com.example.loan_backend;

import com.example.loan_backend.filter.JwtRequestFilter;
import com.example.loan_backend.services.CustomUserDetailsService;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private JwtRequestFilter jwtRequestFilter;
  @Autowired
  @Qualifier("delegatedAuthenticationEntryPoint")
  private AuthenticationEntryPoint authEntryPoint;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration auth)
      throws Exception {
    return auth.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(t -> t.disable())
        .authorizeHttpRequests(
            auth -> auth
                .mvcMatchers("/api-docs/**").permitAll().mvcMatchers("/auth/**").permitAll()
                .mvcMatchers("/admin/**")
                .hasRole("ADMIN")
                .mvcMatchers("/loan/**").hasRole("USER").anyRequest()
                .denyAll())
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .userDetailsService(customUserDetailsService)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(hand -> hand.authenticationEntryPoint(authEntryPoint));

    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public OpenAPI customOpenApi() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearer-key",
            new SecurityScheme().type(Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedMethods(Arrays.asList("*"));
    configuration.setAllowedOrigins(Arrays.asList("*"));
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setExposedHeaders(Arrays.asList("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
