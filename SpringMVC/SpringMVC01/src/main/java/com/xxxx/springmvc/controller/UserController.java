package com.xxxx.springmvc.controller;

import com.xxxx.springmvc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @RequestMapping("/user/Login")
    public ModelAndView userLogin(HttpSession session){
        ModelAndView mv=new ModelAndView();
//        传session
        User user=new User();
        user.setId(10);
        user.setUserName("admin");
        user.setUserPwd("123456");
        session.setAttribute("userInfo",user);
        mv.setViewName("success");
        return mv;
    }
    @RequestMapping("/user/addUser")
    public ModelAndView addUser(){
        System.out.println("添加用户记录。。。");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }
    @RequestMapping("/user/delUser")
    public ModelAndView delUser(){
        System.out.println("删除用户记录。。。");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }
    @RequestMapping("/user/updateUser")
    public ModelAndView updateUser(){
        System.out.println("更新用户记录。。。");
        ModelAndView mv=new ModelAndView();
        mv.setViewName("success");
        return mv;
    }
}
