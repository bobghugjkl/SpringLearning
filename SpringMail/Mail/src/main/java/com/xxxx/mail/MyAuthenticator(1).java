package com.xxxx.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 认证类
 */
public class MyAuthenticator extends Authenticator {

    private String userName;
    private String userPwd;

    public MyAuthenticator(String userName, String userPwd) {
        this.userName = userName;
        this.userPwd = userPwd;
    }

    /**
     * 邮件服务器发送邮件时，进行身份验证
     * @return
     */
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName,userPwd);
    }
}
