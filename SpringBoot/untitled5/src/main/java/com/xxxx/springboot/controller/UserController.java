package com.xxxx.springboot.controller;

import com.xxxx.springboot.service.UserService;
import com.xxxx.springboot.vo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {
    @Resource
    private UserService userService;


    @GetMapping("user/{userName}")
    public User queryUserByUserName(@PathVariable String userName){
        return userService.queryUserByUserName(userName);
    }
}
