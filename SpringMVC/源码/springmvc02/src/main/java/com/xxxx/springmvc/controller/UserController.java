package com.xxxx.springmvc.controller;

import com.xxxx.springmvc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @RequestMapping("user/login")
    public ModelAndView userLogin(HttpSession session){
        ModelAndView mv=new ModelAndView();
        System.out.println("用户登录...");
        mv.setViewName("success");
        User user=new User();
        user.setId(10);
        user.setUserName("admin");
        user.setUserPwd("111111");
        session.setAttribute("userInfo",user);
        return mv;
    }

    @RequestMapping("user/addUser")
    public ModelAndView addUser(){
        System.out.println("用户模块_添加用户操作...");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }

    @RequestMapping("user/updateUser")
    public ModelAndView updateUser(){
        System.out.println("用户模块_更新用户操作...");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }

    @RequestMapping("user/delUser")
        public ModelAndView delUser(){
        System.out.println("用户模块_删除用户操作...");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }

}
