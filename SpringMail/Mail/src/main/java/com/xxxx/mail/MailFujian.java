package com.xxxx.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

//发送普通文本邮件
public class MailFujian {
    public static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.163.com");
        properties.setProperty("mail.smtp.port","25");
        properties.setProperty("mail.smtp.auth","true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","15963300728","YPZLEPJQWLJNZOSW");
        Message message = createHtmlFujian(session);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    private static Message createHtmlFujian(Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom("15963300728@163.com");
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("15963300728@163.com")});
        message.setSubject("test Fujian");
        message.setSentDate(new Date());
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent("<h2>this is a content full of fujian</h2>","text/html;charset=UTF-8");
        MimeBodyPart attachPart = new MimeBodyPart();
        DataHandler df = new DataHandler(new FileDataSource("C:\\Users\\DELL\\Desktop\\文本文档.txt"));
        attachPart.setDataHandler(df);
        attachPart.setFileName(df.getName());
        MimeMultipart mimeMultipart = new MimeMultipart();
        mimeMultipart.addBodyPart(bodyPart);
        mimeMultipart.addBodyPart(attachPart);
        mimeMultipart.setSubType("mixed");
        message.setContent(mimeMultipart);
        return message;
    }


}
