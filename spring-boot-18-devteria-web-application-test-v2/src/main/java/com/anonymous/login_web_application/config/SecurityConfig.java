package com.anonymous.login_web_application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINT = {
        "identity/users", "/roles", "/permissions", "identity/auth/token", "identity/auth/logout", "identity/auth/refresh",
    };

    @Lazy
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> {
                    request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT)
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .oauth2ResourceServer(auth -> {
                    auth.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder).jwtAuthenticationConverter(converter()))
                            .authenticationEntryPoint((request, response, authException) -> {
                                ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
                                response.setStatus(errorCode.getHttpStatusCode().value());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                                ApiResponse<?> apiResponse = ApiResponse.builder()
                                        .code(errorCode.getCode())
                                        .message(errorCode.getMessage())
                                        .build();

                                ObjectMapper objectMapper = new ObjectMapper();

                                response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
                                response.flushBuffer();
                            });
                })
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // cho phep truy cap api tu origin nao (domain / web) nao
        corsConfiguration.addAllowedMethod("*"); // cho phep tat ca cac request co method Get, Post, ...
        corsConfiguration.addAllowedHeader("*"); // cho phep tat ca cac request co header bat ki

        UrlBasedCorsConfigurationSource urlBaseCors = new UrlBasedCorsConfigurationSource();
        urlBaseCors.registerCorsConfiguration("/**", corsConfiguration); // ap dung cors config len tat ca cac endpoint (api)

        return new CorsFilter(urlBaseCors);
    }

    @Bean
    JwtAuthenticationConverter converter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
}
