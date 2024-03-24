package com.xxxx.ssm.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xxxx.ssm.service.UserService;
import com.xxxx.ssm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class HelloController {
    @Resource
    private UserService userService;

    @RequestMapping("hello")
    public String hello(Integer userId, Model model){
        User user = userService.queryUserByUserId(userId);
        model.addAttribute("user",user);
        return "hello0";
    }
}
