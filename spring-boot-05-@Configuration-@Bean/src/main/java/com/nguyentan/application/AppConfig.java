package com.nguyentan.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    SimpleBean simpleBean() {
        return new SimpleBean("tan nguyen");
    }

    @Bean
    MysqlConnector mysqlConnector() {
        MysqlConnector mysqlConnector = new MysqlConnector();
        mysqlConnector.setUrl("jdbc:mysql://host1:33060/loda");
        return mysqlConnector;
    }

    @Bean
    MongoDbConnector mongoDbConnector() {
        MongoDbConnector mongoDbConnector = new MongoDbConnector();
        mongoDbConnector.setUrl("mongodb://mongodb0.example.com:27017/loda");
        return mongoDbConnector;
    }

    @Bean
    PostgreConnector postgreConnector() {
        PostgreConnector postgreConnector = new PostgreConnector();
        postgreConnector.setUrl("postgresql://localhost/loda");
        return postgreConnector;
    }
}
