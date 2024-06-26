package com.xxxx.springmvc.controller;

import com.xxxx.springmvc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserLogInController {
    @RequestMapping("user/queryUser")
    @ResponseBody
    public User queryUser(){
        User user = new User();
        user.setId(20);
        user.setUserName("admin");
        user.setUserPwd("123456");
        return user;
    }
    @RequestMapping("user/queryUsers")
    @ResponseBody
    public List<User>queryUsers(){
        List<User> users = new ArrayList<User>();
        User user01 = new User();
        user01.setId(20);
        user01.setUserPwd("123456");
        user01.setUserName("admin");
        User user02 = new User();
        user02.setId(30);
        user02.setUserPwd("斋藤飞鸟");
        user02.setUserName("西野七濑");
        users.add(user01);
        users.add(user02);
        return users;
    }
    @RequestMapping("user/queryUser03")
    @ResponseBody
    public User queryUser03(@RequestBody User user){
        return user;
    }


}
