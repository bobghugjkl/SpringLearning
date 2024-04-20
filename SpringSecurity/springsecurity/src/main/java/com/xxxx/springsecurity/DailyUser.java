package com.xxxx.springsecurity;

public class DailyUser {
    private String User;
    private String username;
    private int password;
    private int age;

    public String getUser() {
        return User;
    }

    public DailyUser() {
    }

    public void setUser(String user) {
        User = user;
    }

    public DailyUser(String user, String username, int password, int age) {
        User = user;
        this.username = username;
        this.password = password;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public void say(){
        System.out.println("my name is...");
    }
}
