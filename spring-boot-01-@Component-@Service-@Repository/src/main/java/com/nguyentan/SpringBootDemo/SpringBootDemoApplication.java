package com.nguyentan.SpringBootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootDemoApplication.class, args);

		GirlService girlService = context.getBean(GirlService.class);
		Girl girl = girlService.getRandomGirl();

		System.out.println(girl);
	}
}
