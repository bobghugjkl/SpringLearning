package com.xxxx.Service;

import com.xxxx.Dao.IuserDao;
import com.xxxx.Dao.UserDao;

import javax.annotation.Resource;

public class UserService {
    @Resource
    private UserDao userDao;
    @Resource(name = "userDao01")
    private IuserDao iuserDao;

    public void test(){
        userDao.test();
        iuserDao.test();
        System.out.println("Userservice running....");
    }
}
