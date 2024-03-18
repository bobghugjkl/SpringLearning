package com.xxxx.mail;

import java.util.ArrayList;
import java.util.List;

public class MailTest04 {

    public static void main(String[] args) {
        // 发送HTML内容的邮件
        // testHtmlMail();

        // 发送包含附件的邮件
        testAttachMail();

    }

    /**
     * 发送HTML内容的邮件
     */
    public static void testHtmlMail() {
        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setServerHost("smtp.163.com");
        mailSendInfo.setServerPort("25");
        mailSendInfo.setUserName("mail_test01");
        mailSendInfo.setUserPwd("DZKKZCHMXPBGTMZF");
        mailSendInfo.setSubject("邮件封装");
        mailSendInfo.setFromAddress("mail_test01@163.com");
        mailSendInfo.setContent("<h2>这是封装后发送的邮件</h2>");
        List<String> users = new ArrayList<>();
        users.add("mail_test01@163.com");
        mailSendInfo.setToAddrsss(users);
        MailSender mailSender = new MailSender();
        mailSender.sendMail(mailSendInfo);
    }


    /**
     * 发送包含附件的邮件
     */
    public static void testAttachMail() {
        MailSendInfo mailSendInfo = new MailSendInfo();
        mailSendInfo.setServerHost("smtp.163.com");
        mailSendInfo.setServerPort("25");
        mailSendInfo.setUserName("mail_test01");
        mailSendInfo.setUserPwd("DZKKZCHMXPBGTMZF");
        mailSendInfo.setSubject("邮件封装");
        mailSendInfo.setFromAddress("mail_test01@163.com");
        mailSendInfo.setContent("<h2>包含附件的邮件</h2>");
        List<String> users = new ArrayList<>();
        users.add("mail_test01@163.com");
        mailSendInfo.setToAddrsss(users);

        // 添加附件
        List<String> files=new ArrayList<String>();
        files.add("C:\\work\\day11\\004_reference\\邮件附件.txt");
        files.add("C:\\work\\day11\\004_reference\\名单.xlsx");
        mailSendInfo.setAttachFileNames(files);
        MailSender mailSender=new MailSender();
        mailSender.sendMail(mailSendInfo);
    }
}
