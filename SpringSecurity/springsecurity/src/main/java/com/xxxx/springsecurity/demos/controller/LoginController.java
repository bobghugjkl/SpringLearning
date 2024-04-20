package com.xxxx.springsecurity.demos.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("toMain")
    public String toMain(){
        System.out.println("执行登录方法");
        return "redirect:main.html";
    }
}