package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ViewController {
    @RequestMapping("queryView1")
    public String queryView1(){
        return "redirect:v1.jsp";
    }
    @RequestMapping("queryView2")
    public String queryView2(){
        return "redirect:v1.jsp?a=admin&b=123456";
    }
    @RequestMapping("queryView3")
    public String queryView3(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("a", "斋藤飞鸟");
        redirectAttributes.addAttribute("b", "123456");
        return "redirect:v1.jsp";
    }
    @RequestMapping("queryView4")
    public ModelAndView queryView4() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:v1.jsp");
        modelAndView.addObject("a","斋藤飞鸟");
        modelAndView.addObject("b","西野七濑");
        return modelAndView;
    }
    @RequestMapping("queryView5")
    public ModelAndView queryView5(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:test.do");
        return modelAndView;
    }

    @RequestMapping("queryView6")
    public ModelAndView queryView6(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("forward:test.do?a=admin&b=123456");
        return modelAndView;
    }
    @RequestMapping("queryView7")
    public ModelAndView queryView7(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:test.do");
        modelAndView.addObject("a","斋藤飞鸟");
        modelAndView.addObject("b","西野七濑");
        return modelAndView;
    }
}
