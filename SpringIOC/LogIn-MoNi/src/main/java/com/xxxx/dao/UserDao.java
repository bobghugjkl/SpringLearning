package com.xxxx.dao;

import com.xxxx.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final String USERNAME = "admin";
    private final String USERPWD = "admin";
    public User queryUserByUsername(String username){
        User user = null;
        if(!USERNAME.equals(username)){
            return null;
        }
        user = new User();
        user.setUsername(USERNAME);
        user.setUserpassword(USERPWD);
        return user;
    }
}
