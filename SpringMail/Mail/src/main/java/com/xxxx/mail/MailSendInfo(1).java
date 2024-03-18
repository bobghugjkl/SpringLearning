package com.xxxx.mail;

import java.util.List;

/**
 * 邮件发送信息类
 *   定义发送邮件时 邮件服务器 端口 发送方用户名 密码等字段
 */
public class MailSendInfo {

    private String serverHost; // 服务器主机
    private String serverPort; // 服务器端口

    private String fromAddress; // 发送方的邮箱地址
    private List<String> toAddrsss; // 接收方的邮箱地址集合

    private String userName; // 邮箱的用户名
    private String userPwd; // 邮箱的密码 （授权码）

    private String subject; // 邮件主题
    private String content; // 邮件内容

    private Boolean flag = true; // 是否需要身份认证
    private List<String> attachFileNames; // 附件名称集合

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public List<String> getToAddrsss() {
        return toAddrsss;
    }

    public void setToAddrsss(List<String> toAddrsss) {
        this.toAddrsss = toAddrsss;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public List<String> getAttachFileNames() {
        return attachFileNames;
    }

    public void setAttachFileNames(List<String> attachFileNames) {
        this.attachFileNames = attachFileNames;
    }
}
