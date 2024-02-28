package com.xxxx.controller;

import com.xxxx.entity.vo.MessageModel;
import com.xxxx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    public MessageModel userLogin(String uname,String upsd){
        MessageModel messageModel = userService.checkUserLogin(uname,upsd);
        return messageModel;
    }
}
