package com.nguyentan.demoApplication;

import com.nguyentan.demoApplication.enitities.User;
import com.nguyentan.demoApplication.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);

		UserRepository userRepository = context.getBean(UserRepository.class);

		int hp = 10;
		int stamina = 11;

		List<User> users = userRepository.findUserDistinctByHpOrStamina(hp, stamina);

		System.out.println(users);


	}
}
