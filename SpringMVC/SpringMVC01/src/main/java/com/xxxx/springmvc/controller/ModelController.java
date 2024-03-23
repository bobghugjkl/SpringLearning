package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ModelController {
    @RequestMapping("m02")
    public String m02(ModelMap request){
        request.addAttribute("hello","hello m02");
        return "hello";
    }
}
