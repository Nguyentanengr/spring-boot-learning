package com.anonymous.shop_application.configurations;

import com.anonymous.shop_application.dtos.responses.ApiResponse;
import com.anonymous.shop_application.exceptions.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINT = {"api/v1/auth/login"};

    @Lazy
    @Autowired
    CustomJWTDecoder customJWTDecoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()
                                .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth -> {
                    oauth.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJWTDecoder))
                            .authenticationEntryPoint((request, response, authException) -> {
                                ErrorCode errorCode = ErrorCode.USER_UNAUTHENTICATED;
                                response.setStatus(errorCode.getHttpStatus().value());
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
                .csrf(AbstractHttpConfigurer::disable)
        ;


        return http.build();
    }
}
