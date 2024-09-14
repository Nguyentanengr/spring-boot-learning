package com.anonymous.login_web_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class
LoginWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginWebApplication.class, args);
	}

}

/*

content:

1. Authentication with oauth2 (authenticate with token) and authorize

2. Using PostAuthorize & PreAuthorize to authorize user by role, permission

3. Catch exception in filter chain of Spring Security





 */
