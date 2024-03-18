package com.xxxx.mail;

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
public class MailHtml {
    public static void main(String[] args) throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host","smtp.163.com");
        properties.setProperty("mail.smtp.port","25");
        properties.setProperty("mail.smtp.auth","true");

        Session session = Session.getInstance(properties);
        session.setDebug(true);
        Transport transport = session.getTransport();
        transport.connect("smtp.163.com","15963300728","YPZLEPJQWLJNZOSW");
        Message message = createHtmlMail(session);
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }

    private static Message createHtmlMail(Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom("15963300728@163.com");
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("15963300728@163.com")});
        message.setSubject("test Html");
        message.setSentDate(new Date());
        MimeMultipart mimeMultipart = new MimeMultipart();
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body><a href = 'http://www.baidu.com'>baidu</a></body></html>");
        mimeBodyPart.setContent(sb.toString(),"text/html;charset=UTF-8");
        mimeMultipart.addBodyPart(mimeBodyPart);
        message.setContent(mimeMultipart);
        return message;
    }


}
