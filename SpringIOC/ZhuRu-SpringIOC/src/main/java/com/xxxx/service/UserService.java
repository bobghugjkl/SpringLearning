package com.xxxx.service;

import com.xxxx.dao.UserDao;

public class UserService {
//    手动实例化
//    private UserDao userDao = new UserDao();
//    业务逻辑对象javaBean对象，set方法注入
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void test(){
        System.out.println("Running....");
        userDao.test();
    }
}
