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

/**
 * 发送HTML内容的邮件
 *
 *      使用JavaMail发送邮件的步骤
 *          1. 创建Session对象，加载Properties对象
 *          2. 通过Session对象得到transport对象
 *          3. 使用邮箱的用户名和密码连接邮件服务器
 *          4. 设置Message邮件对象
 *          5. 发送邮件
 */
public class MailTest02 {

    public static void main(String[] args) throws MessagingException {

        // 设置邮箱服务器的相关配置
        Properties properties = new Properties();
        // 设置邮服务器的主机名
        properties.setProperty("mail.smtp.host","smtp.163.com");
        // s设置邮箱服务器的端口
        properties.setProperty("mail.smtp.port","25");
        // 设置邮箱服务器是否需要身份认证 （设置为true表示需要身份认证）
        properties.setProperty("mail.smtp.auth","true");


        /* 使用JavaMail发送邮件 */
        // 1. 创建Session对象，加载Properties对象
        Session session = Session.getInstance(properties);
        // 开启Session的debug模式，可以在控制台看到邮件发送的运行状态
        session.setDebug(true);
        // 2. 通过Session对象得到transport对象
        Transport transport = session.getTransport();
        // 3. 使用邮箱的用户名和密码连接邮件服务器 （用户名：@符号前面的内容； 密码：授权码）
        transport.connect("smtp.163.com","mail_test01","DZKKZCHMXPBGTMZF");
        // 4. 设置Message邮件对象
        Message message = createHtmlMail(session);
        // 5. 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        transport.close();
    }

    /**
     * 发送html内容的邮件
     *      创建一封html内容的邮件
     * @param session
     * @return
     */
    private static Message createHtmlMail(Session session) throws MessagingException {

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 设置发件人的邮箱地址
        message.setFrom("mail_test01@163.com");
        // 设置收件人的邮箱地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("mail_test01@163.com"));
        // 设置邮件的主题
        message.setSubject("测试HTML内容");
        // 设置邮件的发送时间
        message.setSentDate(new Date());

        /* 准备邮件数据 */
        // 设置多媒体对象容器 （一个MultiPart对象中可以包含一个或多个BodyPart对象）
        MimeMultipart multipart = new MimeMultipart();

        // 设置邮件体对象
        MimeBodyPart bodyPart = new MimeBodyPart();
        // 设置HTML内容
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body><a href='http://www.baidu.com'>百度一下</a></body></html>");
        // 将HTML内容设置到邮件体对象中
        bodyPart.setContent(sb.toString(),"text/html;charset=UTF-8");

        // 将邮件体对象设置到多媒体对象容器中
        multipart.addBodyPart(bodyPart);

        // 将多媒体对象容器设置到message邮件对象中
        message.setContent(multipart);

        // 返回创建好的邮件对象
        return message;
    }

}
