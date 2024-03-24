package com.xxxx.ssm.service;

import com.github.pagehelper.PageException;
import com.xxxx.ssm.dao.UserDao;
import com.xxxx.ssm.exceptions.BusinessException;
import com.xxxx.ssm.exceptions.ParamsException;
import com.xxxx.ssm.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    public User queryUserByUserId(Integer userId){
        if(1==1){
            throw  new ParamsException();
        }
        return  userDao.queryUserByUserId(userId);
    }
}
