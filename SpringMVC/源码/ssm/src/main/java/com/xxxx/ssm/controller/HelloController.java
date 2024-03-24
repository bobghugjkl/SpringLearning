package com.xxxx.ssm.controller;

import com.mysql.fabric.xmlrpc.base.Param;
import com.xxxx.ssm.exceptions.BusinessException;
import com.xxxx.ssm.exceptions.ParamsException;
import com.xxxx.ssm.service.UserService;
import com.xxxx.ssm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class HelloController extends BaseController{

    @Resource
    private UserService userService;

    @RequestMapping("hello")
    public String hello(Integer userId, Model model){
       /*int a=1/0;*/
       User user = userService.queryUserByUserId(userId);
       model.addAttribute("user",user);
       return "hello";
    }
}
