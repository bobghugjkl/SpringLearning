package com.xxxx.springMail;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MailTest {

    public static void main(String[] args) {

        // 加载Spring的上下文环境
        BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
        SimpleOrderManager simpleOrderManager = (SimpleOrderManager) factory.getBean("simpleOrderManager");
        simpleOrderManager.placeOrder();

    }
}
