package com.xxxx;

import com.xxxx.Service.AccountService;
import com.xxxx.Service.UserService;
import javafx.application.Application;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContextExtensionsKt;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.applet.AppletContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");

        UserService userService = (UserService) ac.getBean("userService");
        userService.test();
        AccountService accountService = (AccountService) ac.getBean("accountService");
        accountService.test();
    }
}
