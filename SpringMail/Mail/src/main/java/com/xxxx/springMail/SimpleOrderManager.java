package com.xxxx.springMail;


import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SimpleOrderManager implements OrderManager {

    @Resource
    private MailSender mailSender;
    @Resource
    private SimpleMailMessage templateMessage;

    @Override
    public void placeOrder() {
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        msg.setTo("mail_test01@163.com");
        msg.setText("Hello Spring Mail");
        try{
            this.mailSender.send(msg);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
