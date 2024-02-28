package com.xxxx.test;

import com.xxxx.controller.UserController;
import com.xxxx.entity.vo.MessageModel;
import com.xxxx.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        UserController userController = (UserController) ac.getBean("userController");
        MessageModel messageModel = userController.userLogin("admin","123");
        System.out.println(messageModel.getResultcode()+" "+messageModel.getResultMessage());
    }
}
