package com.xxxx.springMail;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

public class MailTest02 {

    public static void main(String[] args) throws MessagingException {

        // 加载Spring的上下文环境
        BeanFactory factory = new ClassPathXmlApplicationContext("spring.xml");
        // 获取邮件发送器的bean对象
        JavaMailSender mailSender = (JavaMailSender) factory.getBean("mailSender");
        // 获取邮件对象
        MimeMessage message =  mailSender.createMimeMessage();
        // 设置邮件主题
        message.setSubject("Spring Mail测试");
        // 创建带有附件的消息帮助类
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        // 设置邮件接收者
        helper.setTo("mail_test01@163.com");
        // 设置邮件的发送者
        helper.setFrom("mail_test01@163.com");
        // 设置邮件内容
        helper.setText("这是附件邮件测试！");

        // 设置附件
        File file = new File("C:\\work\\day11\\004_reference\\邮件附件.txt");
        // 添加附件
        helper.addAttachment(file.getName(), file);
        // 发送邮件
        mailSender.send(message);


    }
}
