package com.nguyentan.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Application.class, args);

		try {
			SimpleBean simpleBean = context.getBean(SimpleBean.class);
			System.out.println(simpleBean);
		} catch (Exception e) {
			System.out.println("Get beans is fail!");

		}

		try {
			MysqlConnector mysqlConnector = context.getBean(MysqlConnector.class);
			mysqlConnector.connect();
		} catch (Exception e) {
			System.out.println("Connecting with Mysql Database is fail");
		}

		try {
			MongoDbConnector mongoDbConnector = context.getBean(MongoDbConnector.class);
			mongoDbConnector.connect();
		} catch (Exception e) {
			System.out.println("Connecting with MongoDb Database is fail");
		}

		try {
			PostgreConnector postgreConnector = context.getBean(PostgreConnector.class);
			postgreConnector.connect();
		} catch (Exception e) {
			System.out.println("Connecting with Postgre Database is fail");
		}
	}

}
