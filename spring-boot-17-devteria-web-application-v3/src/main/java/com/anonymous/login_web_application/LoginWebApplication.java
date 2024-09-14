package com.anonymous.login_web_application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginWebApplication.class, args);
	}

}

/*

content:

1. Validation with custom annotation and add dynamic message

2. Logout and invalid token by config decoder using CustomJwtDecoder class

3. enable refresh token in expired time token

 */
