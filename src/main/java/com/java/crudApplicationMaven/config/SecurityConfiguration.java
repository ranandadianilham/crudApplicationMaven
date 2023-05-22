package com.java.crudApplicationMaven.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.crudApplicationMaven.payload.response.BaseResponse;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    BaseResponse errorResponse = new BaseResponse();
                    errorResponse.setCode(HttpStatus.UNAUTHORIZED.value());
                    errorResponse.setDesc("Unauthorized Access");
                    errorResponse.setData(null);
                    // String json = String.format("{\"message\": \"%s\"}", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));

                }).accessDeniedHandler((request, response, accessDeniedException) -> {
                    BaseResponse errorResponse = new BaseResponse();
                    errorResponse.setCode(HttpStatus.FORBIDDEN.value());
                    errorResponse.setDesc("Access Denied");
                    errorResponse.setData(null);

                    // Set the HTTP response status and write the error response as JSON
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                })
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/h2/**"))
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().disable();
        return http.build();
    }
}
