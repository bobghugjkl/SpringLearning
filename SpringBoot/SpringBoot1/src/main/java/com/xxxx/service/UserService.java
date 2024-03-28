package com.xxxx.service;

import com.xxxx.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    public void test(){
        userDao.test();
        System.out.println("UserService test...");
    }
}
