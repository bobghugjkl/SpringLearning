package com.xxxx.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

//发送普通文本邮件
public class MailTest {
    public static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.163.com");
        properties.setProperty("mail.smtp.port","25");
        properties.setProperty("mail.smtp.auth","true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","15963300728","YPZLEPJQWLJNZOSW");
        Message message = createSimpleMail(session);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    private static Message createSimpleMail(Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom("15963300728@163.com");
        message.setRecipients(Message.RecipientType.TO, String.valueOf(new InternetAddress("15963300728@163.com")));
        message.setSubject("test...");
        message.setSentDate(new Date());
        message.setText("hello");

        return message;
    }
}
