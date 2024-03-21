package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParamsController {
    @RequestMapping("p01")
    public void p01(@RequestParam(name = "a",defaultValue = "10") int age,@RequestParam(defaultValue = "10") double s){
        System.out.println("age"+age+".s:"+s);
    }
    @RequestMapping("p04")
    public void p04(@RequestParam(name = "abc",defaultValue = "null") String userName,String userPwd){
        System.out.println(userName+" "+userPwd);
    }
    @RequestMapping("p05")
    public void p04(Integer age ,Double s){
        System.out.println(age+""+s);
    }
    @RequestMapping("p06")
    public void p06(Integer[] ids){
        for (Integer id :
                ids) {
            System.out.println(id);
        }
    }

}
