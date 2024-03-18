package com.xxxx.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 邮件发送器
 */
public class MailSender {

    /**
     * 发送邮件
     * @param mailSendInfo
     */
    public void sendMail(MailSendInfo mailSendInfo) {

        try {
            // 设置邮箱服务器的相关配置
            Properties properties = new Properties();
            // 设置邮服务器的主机名
            properties.setProperty("mail.smtp.host",mailSendInfo.getServerHost());
            // s设置邮箱服务器的端口
            properties.setProperty("mail.smtp.port",mailSendInfo.getServerPort());
            // 设置邮箱服务器是否需要身份认证 （设置为true表示需要身份认证）
            properties.setProperty("mail.smtp.auth",mailSendInfo.getFlag().toString());
            // 身份认证
            MyAuthenticator myAuthenticator = new MyAuthenticator(mailSendInfo.getUserName(),mailSendInfo.getUserPwd());

            // 创建Session对象
            Session session = Session.getDefaultInstance(properties, myAuthenticator); // 身份验证
            // 开启session的debug模式
            session.setDebug(true);

            /* 设置邮件内容 */
            // 创建message对象
            Message message = new MimeMessage(session);

            // 设置发送方的邮箱地址
            Address from = new InternetAddress(mailSendInfo.getFromAddress());
            message.setFrom(from);

            // 设置邮件的主题
            message.setSubject(mailSendInfo.getSubject());
            // 设置邮件的发送时间
            message.setSentDate(new Date());

            // 设置接收方的邮箱地址
            // 判断收件方的集合是否为空
            if (null != mailSendInfo.getToAddrsss() && mailSendInfo.getToAddrsss().size() > 0) {
                // 定义地址数组
                Address[] addresses = new Address[mailSendInfo.getToAddrsss().size()];
                // 遍历邮箱地址集合
                for (int i = 0; i < mailSendInfo.getToAddrsss().size(); i++) {
                    // 得到每个地址对象
                    Address address = new InternetAddress(mailSendInfo.getToAddrsss().get(i));
                    // 将地址设置到数组中
                    addresses[i] = address;
                }
                // 将接收者的邮箱地址数组设置到message邮件对象中
                message.setRecipients(Message.RecipientType.TO, addresses);
            }


            // 创建多媒体对象容器
            MimeMultipart multipart = new MimeMultipart();

            // 创建正文内容
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(mailSendInfo.getContent(),"text/html;charset=UTF-8");
            // 将正文内容的bodyPart对象设置到多媒体容器中
            multipart.addBodyPart(bodyPart);

            // 获取所有的附件文件名
            List<String> files = mailSendInfo.getAttachFileNames();
            // 判断文件是否存在
            if (null != files && files.size() > 0) {
                // 遍历，获取对应的附件对象
                for(int i = 0; i < files.size(); i++) {
                    // 附件对象
                    File file = new File(files.get(i));
                    // 判断附件对象是否存在
                    if (file.exists()) {
                        // 如果附件存在，创建附件对象
                        MimeBodyPart attachPart = new MimeBodyPart();
                        DataHandler df = new DataHandler(new FileDataSource(file));
                        attachPart.setDataHandler(df);
                        // 设置附件文件名 （解决附件名乱码）
                        attachPart.setFileName(MimeUtility.encodeText(df.getName()));
                        // 添加附件 （将attachPart对象设置到MultiPart对象中）
                        multipart.addBodyPart(attachPart);
                    }
                }
            }

            // 设置邮件对象 （将多媒体对象容器设置到message对象中）
            message.setContent(multipart);

            // 发送邮件
            Transport.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
