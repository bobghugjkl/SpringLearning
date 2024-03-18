package com.xxxx.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailSender {
    public void sendMail(MailSendInfo mailSendInfo){
        Message message = null;
        Session session = null;
        try{
            Properties properties = new Properties();
            properties.setProperty("mail.smtp.host",mailSendInfo.getServerHost());
            properties.setProperty("mail,smtp.host",mailSendInfo.getPort());
            properties.setProperty("mail.smtp.host",mailSendInfo.getFlag().toString());
            MyAuthenticator myAuthenticator = new MyAuthenticator(mailSendInfo.getUserName(),mailSendInfo.getUserPwd());
            session = Session.getDefaultInstance(properties,myAuthenticator);
            session.setDebug(true);
            message = new MimeMessage(session);

            Address from = new InternetAddress(mailSendInfo.getFromAddress());
            message.setFrom(from);
            message.setSubject(mailSendInfo.getSubject());
            message.setSentDate(new Date());
            if(null != mailSendInfo.getToAddress() && mailSendInfo.getToAddress().size() > 0){
                Address[] addresses = new Address[mailSendInfo.getToAddress().size()];
                for(int i = 0;i<mailSendInfo.getToAddress().size();i++){
                    Address address = new InternetAddress(mailSendInfo.getToAddress().get(i));
                    addresses[i] = address;
                }
                message.setRecipients(Message.RecipientType.TO,addresses);
            }

            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(mailSendInfo.getContent(),"text/html;charset=UTF-8");
            mimeMultipart.addBodyPart(bodyPart);
            List<String> files = mailSendInfo.getAttachFileNames();
            if(null != files&&files.size()>0){
                for(int i = 0;i<files.size();i++){
                    File file = new File(files.get(i));
                    if(file.exists()){
                        MimeBodyPart attachPart = new MimeBodyPart();
                        DataHandler dataHandler = new DataHandler(new FileDataSource(file));
                        attachPart.setDataHandler(dataHandler);
                        attachPart.setFileName(MimeUtility.encodeText(dataHandler.getName()));
                    }
                }
            }

            message.setContent(mimeMultipart);
            Transport.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
