package com.xxxx.mail;

import java.util.ArrayList;
import java.util.List;

public class MailTest04 {
    public static void main(String[] args) {
        testHtmlMail();
    }
    public static void testHtmlMail(){
        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setServerHost("smtp.163.com");
        mailSendInfo.setServerHost("25");

        mailSendInfo.setUserName("15963300728");
        mailSendInfo.setUserPwd("YPZLEPJQWLJNZOSW");
        mailSendInfo.setSubject("邮件封装");
        mailSendInfo.setFromAddress("15963300728@163.com");
        mailSendInfo.setContent("<h2>这是封装后发送的邮件</h2>");
        List<String> users = new ArrayList<>();
        users.add("15963300728@163.com");
        mailSendInfo.setToAddress(users);
        MailSender mailSender=new MailSender();
        mailSender.sendMail(mailSendInfo);
    }
}
