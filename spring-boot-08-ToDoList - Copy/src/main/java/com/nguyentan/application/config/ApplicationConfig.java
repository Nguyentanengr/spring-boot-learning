package com.nguyentan.application.config;

import com.nguyentan.application.model.ToDoValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ToDoValidator validator() {
        return new ToDoValidator();
    }
}
