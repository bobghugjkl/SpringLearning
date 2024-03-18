package com.xxxx.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * 发送普通文本的邮件
 *
 *      使用JavaMail发送邮件的步骤
 *          1. 创建Session对象，加载Properties对象
 *          2. 通过Session对象得到transport对象
 *          3. 使用邮箱的用户名和密码连接邮件服务器
 *          4. 设置Message邮件对象
 *          5. 发送邮件
 */
public class MailTest01 {

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
        Message message = createSimpleMail(session);
        // 5. 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        transport.close();
    }

    /**
     * 普通文本的邮件
     *      创建一封普通文本的邮件
     * @param session
     * @return
     */
    private static Message createSimpleMail(Session session) throws MessagingException {

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 设置邮件的发送人
        message.setFrom("mail_test01@163.com");
        // 设置邮件的接收人 （发件人和收件人是同一个账户，邮箱也是一样的）
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("mail_test01@163.com"));

        // 设置邮件的主题
        message.setSubject("测试文本邮件");
        // 设置发送日期
        message.setSentDate(new Date());
        // 设置邮件的文本内容
        message.setText("你好，这是一封测试文本的邮件！");

        // 返回封装好的邮箱对象
        return message;
    }
}
