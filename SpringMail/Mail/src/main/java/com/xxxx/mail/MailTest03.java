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
public class MailTest03 {

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
        Message message = createAttachMail(session);
        // 5. 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        transport.close();
    }

    /**
     * 发送包含附件的邮件
     *      创建一封包含附件的邮件
     * @param session
     * @return
     */
    private static Message createAttachMail(Session session) throws MessagingException {

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);

        // 设置发件人的邮箱地址
        message.setFrom("mail_test01@163.com");
        // 设置收件人的邮箱地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("mail_test01@163.com"));
        // 设置邮件的主题
        message.setSubject("测试包含附件的邮件");
        // 设置邮件的发送时间
        message.setSentDate(new Date());

        /* 创建邮件内容 */

        // 创建邮件正文
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent("<h2>这是一封包含附件的邮件</h2>","text/html;charset=UTF-8");

        // 创建附件对象
        MimeBodyPart attachPart = new MimeBodyPart();
        // 本地文件
        DataHandler df = new DataHandler(new FileDataSource("C:\\work\\day11\\004_reference\\邮件附件.txt"));
        // 将本地文件设置到附件对象中
        attachPart.setDataHandler(df);
        // 设置附件文件名
        attachPart.setFileName(df.getName());

        // 创建多媒体容器对象
        MimeMultipart multipart = new MimeMultipart();
        // 添加正文
        multipart.addBodyPart(bodyPart);
        // 添加附件
        multipart.addBodyPart(attachPart);
        // 如果邮件中要添加附件，设置为mixed
        multipart.setSubType("mixed");

        // 将多媒体容器对象设置到邮件对象
        message.setContent(multipart);

        // 返回创建好的邮件对象
        return  message;

    }


}
