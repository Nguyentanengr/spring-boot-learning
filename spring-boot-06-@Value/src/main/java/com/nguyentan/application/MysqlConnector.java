package com.nguyentan.application;

public class MysqlConnector extends DatabaseConnector{
    @Override
    public void connect() {
        System.out.println("Connected with Mysql Database at " + getUrl());
    }

}
