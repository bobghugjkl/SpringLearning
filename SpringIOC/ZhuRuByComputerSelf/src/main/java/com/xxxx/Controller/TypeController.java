package com.xxxx.Controller;

import com.xxxx.Service.TypeService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class TypeController {
    @Resource
    private TypeService typeService;
    public void test(){
        typeService.test();
        System.out.println("TypeController running...");
    }
}
