package com.xxxx.ssm.service;

import com.xxxx.ssm.dao.UserDao;
import com.xxxx.ssm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public User queryUserByUserId(Integer userId){
        return userDao.queryUserByUserId(userId);
    }
}
