package com.xxxx.dao;

import org.springframework.stereotype.Repository;
//给ioc做实例化
@Repository
public class UserDao {
    public void test(){
        System.out.println("UserDao test...");
    }
}
