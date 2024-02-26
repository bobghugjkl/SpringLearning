package com.xxxx.service;

import com.xxxx.dao.StudentDao;
import com.xxxx.dao.UserDao;
import com.xxxx.dao.UserDao02;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/*
构造器注入需要带参构造
 */
public class UserService02 {
    private UserDao02 userDao02;

    private StudentDao studentDao;
/*
    构造器注入
     */

    public UserService02(UserDao02 userDao02, StudentDao studentDao) {
        this.userDao02 = userDao02;
        this.studentDao = studentDao;
    }

    public void test(){
        System.out.println("Running....");
    }
}
