package com.nguyentan.application;

public class SimpleBean {
    private String username;

    public SimpleBean(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Get a SimpleBean with name is " + username;
    }
}
