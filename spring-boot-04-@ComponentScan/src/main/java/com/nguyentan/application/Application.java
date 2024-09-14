package com.nguyentan.application;

import com.nguyentan.application.others.OtherGirl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


//@ComponentScan("com.nguyentan.application.others") chi thuc hien quet Beans trong cac thu muc con
@SpringBootApplication(scanBasePackages = {"com.nguyentan.application.others", "com.nguyentan.application"})
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		try {
			Girl girl = context.getBean(Girl.class);
			System.out.println(girl);
		} catch (Exception e) {
			System.out.println("Bean Girl not exist!");
		}

		try {
			OtherGirl otherGirl = context.getBean(OtherGirl.class);
			System.out.println(otherGirl);
		} catch (Exception e) {
			System.out.println("Bean OtherGirl not exist!");
		}
	}
}
