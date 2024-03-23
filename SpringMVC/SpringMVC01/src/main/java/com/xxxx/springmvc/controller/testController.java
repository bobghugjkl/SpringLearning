package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {
    @RequestMapping("test")
    public void test(String a,String b){
        System.out.println("TestController -> test"+":"+a+" "+b);
    }
}
