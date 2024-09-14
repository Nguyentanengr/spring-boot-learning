package com.nguyentan.application;


public class MongoDbConnector extends DatabaseConnector {

    @Override
    public void connect() {
        System.out.println("Connected with MongoDb Database at " + getUrl());
    }
}
