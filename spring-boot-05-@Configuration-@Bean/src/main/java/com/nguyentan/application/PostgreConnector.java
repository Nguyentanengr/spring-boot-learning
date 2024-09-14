package com.nguyentan.application;

public class PostgreConnector extends DatabaseConnector {
    @Override
    public void connect() {
        System.out.println("Connected with Postgre Database at " + getUrl());
    }
}
