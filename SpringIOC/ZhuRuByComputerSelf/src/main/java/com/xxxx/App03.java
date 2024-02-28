package com.xxxx;

import com.xxxx.Service.RoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.AbstractCollection;

public class App03 {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring03.xml");
//        RoleService roleService = (RoleService) applicationContext.getBean("roleService");
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("spring03.xml");
        System.out.println(ctx.getBean("roleService"));
        ctx.close();
    }
}
