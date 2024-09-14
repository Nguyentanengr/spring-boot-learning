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

1. Using RestController, PostMapping, GetMapping, ... to working with Restful API

2. Using JWT library in authentication and config to create token and introspect token

3. Using basic SpringSecurity to authorize for user

4. Using mapstruct to map dto with entity

5. Catch Global exception

6. Using Validator to check valid dto

 */
