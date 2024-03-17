---
typora-root-url: images
typora-copy-images-to: images
---

# Spring 邮件发送

## 主要内容

![](/Spring邮件发送.png)

## JavaMail 概述

​	JavaMail，顾名思义，提供给开发者处理电子邮件相关的编程接口。JavaMail 是由 Sun 定义的一套收发电子邮件的 API，它可以方便地执行一些常用的邮件传输，不同的厂商可以提供自己的实现类。但它并没有包含在 JDK 中，而是作为 JavaEE 的一部分。

​	厂商所提供的 JavaMail 服务程序可以有选择地实现某些邮件协议，常见的邮件协议包括：

- SMTP：简单邮件传输协议，用于发送电子邮件的传输协议；
- POP3：用于接收电子邮件的标准协议；
- IMAP：互联网消息协议，是 POP3 的替代协议。

​	这三种协议都有对应 SSL 加密传输的协议，分别是 SMTPS，POP3S 和 IMAPS。除 JavaMail 服务提供程序之外， JavaMail 还需要 JAF(JavaBeans Activation Framework)来处理不是纯文本的邮件内容，这包括 MIME（多用途互联网邮件扩展）、URL 页面和文件附件等内容。另外，JavaMail 依赖 JAF(JavaBeans Activation Framework)，JAF 在 Java6 之后已经合并到 JDK 中，而 JDK5 之前需要另外下载 JAF 的类库。



### 协议介绍

​	在研究 JavaMail API 的细则之前，首先需要对于 API 用到的协议有个认识。对于 java mail 来说用到的协议有常见的几种： SMTP、POP、IMAP、MIME

#### SMTP

​	简单邮件传输协议（Simple Mail Transfer Protocol，SMTP）由 RFC 821 定义。它定义了发送电子邮件的机制。在 JavaMail API 环境中，您基于 JavaMail 的程序将和您的公司或因特网服务供应商的（Internet Service
Provider's，ISP's）SMTP 服务器通信。SMTP 服务器会中转消息给接收方 SMTP 服务器以便最终让用户经由 POP 或 IMAP 获得。

#### POP

​	POP 代表邮局协议（Post Office Protocol）。目前用的是版本 3，也称 POP3，RFC 1939 定义了这个协议。POP 是一种机制，因特网上大多数人用它得到邮件。它规定每个用户一个邮箱的支持。这就是它所能做的，而这也造成了许多混淆。使用 POP3 时，用户熟悉的许多性能并不是由 POP 协议支持的，如查看有几封新邮件消息这一性能。这些性能内建于如 Eudora 或 Microsoft Outlook 之类的程序中，它们能记住一些事，诸如最近一次收到的邮件，还能计算出有多少是新的。所以当使用 JavaMail API 时，如果您想要这类信息，您就必须自己算。

#### IMAP

​	IMAP 是更高级的用于接收消息的协议。在 RFC 2060 中被定义，IMAP 代表因特网消息访问协议（Internet Message Access Protocol），目前用的是版本 4，也称 IMAP4。在用到 IMAP 时，邮件服务器必需支持这个协议。不能仅仅把使用 POP 的程序用于 IMAP，并指望它支持 IMAP 所有性能。假设邮件服务器支持 IMAP，基于 JavaMail 的程序可以利用这种情况 — 用户在服务器上有多个文件夹（folder），并且这些文件夹可以被多个用户共享。因为有这一更高级的性能，您也许会认为所有用户都会使用 IMAP。事实并不是这样。要求服务器接收新消息，在用户请求时发送到用户手中，还要在每个用户的多个文件夹中维护消息。这样虽然能将消息集中备份，但随着用户长期的邮件夹越来越大，到磁盘空间耗尽时，每个用户都会受到损失。使用 POP，就能卸载邮件服务器上保存的消息了。

#### MIME

​	MIME 代表多用途因特网邮件扩展标准（Multipurpose Internet Mail Extensions）。它不是邮件传输协议。但对传输内容的消息、附件及其它的内容定义了格式。这里有很多不同的有效文档：RFC 822、RFC 2045、RFC
2046 和 RFC 2047。作为一个 JavaMail API 的用户，您通常不必对这些格式操心。无论如何，一定存在这些格式而且程序会用到它。



### JavaMail 的关键对象

​	JavaMail 对收发邮件进行了高级的抽象，形成了一些关键的的接口和类，它们构成了程序的基础，下面我们分别来了解一下这些最常见的对象。


#### Properties 属性对象

​	由于 JavaMail 需要和邮件服务器进行通信，这就要求程序提供许多诸如服务器地址、端口、用户名、密码等信息，JavaMail 通过 Properties 对象封装这些属性信息。如下面的代码封装了两个属性信息：

```java
Properties props = new Properties();
props.put("mail.smtp.host", "smtp.sina.com.cn");
props.put("mail.smtp.auth", "true");
```

​	针对不同的的邮件协议，JavaMail 规定了服务提供者必须支持一系列属性，下表是针对 SMTP 协议的一些常见属性（属性值都以 String 类型进行设置，属性类型栏仅表示属性是如何被解析的）：

| 属性名                           | 类型    | 说明                                                         |
| -------------------------------- | ------- | ------------------------------------------------------------ |
| mail.smtp.host                   | String  | SMTP 服务器地址，如smtp.sina.com.cn                          |
| mail.smtp.port                   | int     | SMTP 服务器端口号，默认为 25                                 |
| mail.smtp.auth                   | boolean | SMTP 服务器是否需要用户认证，默认为 false                    |
| mail.smtp.user                   | String  | SMTP 默认的登陆用户名                                        |
| mail.smtp.from                   | String  | 默认的邮件发送源地址                                         |
| mail.smtp.socketFactory.class    | String  | socket 工厂类类名<br>通过设置该属性可以覆盖提供者默认的实现，必须实现javax.NET.SocketFactory接口 |
| mail.smtp.socketFactory.port     | int     | 指定 socket 工厂类所用的端口号<br>如果没有规定，则使用默认的端口号 |
| mail.smtp.socketFactory.fallback | boolean | 设置为 true 时，当使用指定的socket 类创建 socket 失败后，将使用 Java.net.Socket 创建socket，默认为 true |
| mail.smtp.timeout                | int     | I/O 连接超时时间，单位为毫秒，默认为永不超时                 |

​	 其他几个协议也有类似的一系列属性，如 POP3 的 mail.pop3.host、mail.pop3.port 以及IMAP 的 mail.imap.host、mail.imap.host 等。

#### Session 会话对象

​	Session 是一个很容易被误解的类，这归咎于混淆视听的类名。千万不要以为这里的 Session 像 HttpSession 一样代表真实的交互会话，但创建 Session 对象时，并没有对应的物理连接，它只不过是一对配置信息的集合。

​	Session 的主要作用，包括两个方面：

1. 接收各种配置属性信息：通过 Properties 对象设置的属性信息；
2. 初始化 JavaMail 环境：根据 JavaMail 的配置文件，初始化 JavaMail 环境，以便通过 Session 对象创建其他重要类的实例。

#### Transport 和 Store 传输和存储

​	邮件操作只有发送或接收两种处理方式，JavaMail 将这两种不同操作描述为传输（javax.mail.Transport）和存储（javax.mail.Store），传输对应邮件的发送，而存储对应邮件的接收。

#### Message 消息对象

​	一旦获得 Session 对象，就可以继续创建要发送的消息。这由 Message 类来完成。因为 Message 是个抽象类，您必需用一个子类，多数情况下为 javax.mail.internet.MimeMessage。MimeMessage 是个能理解 MIME 类型和头的电子邮件消息，正如不同 RFC 中所定义的。虽然在某些头部域非 ASCII 字符也能被译码，但 Message 头只能被限制为用 US-ASCII 字符。

#### Address 地址

​	一旦您创建了 Session 和 Message，并将内容填入消息后，就可以用 Address 确定信件地址了。和 Message 一样，Address 也是个抽象类。您用的是 javax.mail.internet.InternetAddress 类。若创建的地址只包含电子邮件地址，只要传递电子邮件地址到构造器就行了。

#### Authenticator 认证者

​	与 java 类一样，JavaMail API 也可以利用 Authenticator 通过用户名和密码访问受保护的资源。对于 JavaMail API 来说，这些资源就是邮件服务器。JavaMail Authenticator 在 javax.mail 包中，而且它和 java.net 中同名的类 Authenticator 不同。两者并不共享同一个 Authenticator，因为 JavaMail API 用于 Java 1.1，它没有 java.net 类别。要使用 Authenticator，先创建一个抽象类的子类，并从 getPasswordAuthentication() 方法中返回 PasswordAuthentication 实例。创建完成后，您必需向 session 注册 Authenticator。然后，在需要认证的时候，就会通知 Authenticator。您可以弹出窗口，也可以从配置文件中（虽然没有加密是不安全的）读取用户名和密码，将它们作为 PasswordAuthentication 对象返回给调用程序。



## Java Mail 环境准备

### 设置邮箱服务

这里是以163邮箱为例

![](/mail-02.png)

注册 163 邮箱，登录 163 邮箱后，设置邮箱账户开通 smtp 服务

![](/mail-03.png)

![](/mail-04.png)



​	需要根据注册时的手机号发送的验证码来开通获取邮箱客户端授权码。开通成功后，会显示个人授权访问码，该授权码是后面通过 Java mail 发送邮件的认证密码，非常重要。



### 添加依赖

创建 Maven 项目，在 pom.xml 配置文件中添加 mail 的所需的依赖

```xml
<!-- Java Mail -->
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>
<dependency>
    <groupId>javax.mail</groupId>
    <artifactId>javax.mail-api</artifactId>
    <version>1.6.2</version>
</dependency>
```



## Java Mail 发送邮件

### 发送普通文本的邮件

```java
package com.xxxx.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * 发送普通文本的邮件
 */
public class MailTest01 {

    public static void main(String[] args) throws Exception {

        // 定义邮箱服务器配置
        Properties prop = new Properties();
        // 设置邮件服务器主机名 （163 邮件服务器地址："mail.smtp.host"  "smtp.163.com"）
        prop.setProperty("mail.smtp.host", "smtp.163.com");
        // 设置邮件服务器的端口
        prop.setProperty("mail.smtp.port", "25");
        // 设置邮件服务器认证属性 （设置为true表示发送服务器需要身份验证）
        prop.setProperty("mail.smtp.auth", "true");
        // 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证
        // prop.setProperty("mail.smtp.port", "465");
        // prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        // prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        // prop.setProperty("mail.smtp.socketFactory.port", "465");

        // 使用JavaMail发送邮件的5个步骤
        // 1. 创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2. 通过session得到transport对象
        Transport ts = session.getTransport();
        // 3. 使用邮箱的用户名和密码连上邮件服务器（用户名只需写@前面的即可，密码是指授权码）
        ts.connect("smtp.163.com", "用户名", "密码");
        // 4. 创建邮件
        // 发送普通文本的邮件
        Message message = createSimpleTxtMail(session);
        
        // 5. 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        ts.close();

    }

    /**
     * 普通文本邮件
     *      创建一封只包含文本的邮件
     * @param session
     * @return
     */
    public static MimeMessage createSimpleTxtMail(Session session) throws MessagingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的发件人的邮箱地址
        message.setFrom(new InternetAddress("发件人的邮箱地址"));
        // 设置邮件的收件人的邮箱地址 （现在发件人和收件人是一样的，那就是自己给自己发）
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("收件人的邮箱地址"));

        // 发送给多个收件人
        // message.setRecipients(Message.RecipientType.TO, new InternetAddress[] {});
        // Cc: 抄送（可选）
        // message.setRecipient(MimeMessage.RecipientType.CC, new InternetAddress(""));
        // Bcc: 密送（可选）
        // message.setRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(""));

        // 邮件的主题
        message.setSubject("测试文本邮件");
        // 设置发送日期
        message.setSentDate(new Date());
        // 邮件的文本内容 （setText()：纯文本内容）
        message.setText("你好，这是一封测试邮件！");

        // 返回创建好的邮件对象
        return message;
    }
}
```

效果如下：

![](/mail-05.png)

### 发送HTML内容的邮件

```java
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
 */
public class MailTest02 {

    public static void main(String[] args) throws Exception {

        // 定义邮箱服务器配置
        Properties prop = new Properties();
        // 设置邮件服务器主机名 （163 邮件服务器地址："mail.smtp.host"  "smtp.163.com"）
        prop.setProperty("mail.smtp.host", "smtp.163.com");
        // 设置邮件服务器的端口
        prop.setProperty("mail.smtp.port", "25");
        // 设置邮件服务器认证属性 （设置为true表示发送服务器需要身份验证）
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤
        // 1. 创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2. 通过session得到transport对象
        Transport ts = session.getTransport();
        // 3. 使用邮箱的用户名和密码连上邮件服务器（用户名只需写@前面的即可，密码是指授权码）
        ts.connect("smtp.163.com", "用户名", "密码");
        // 4. 创建邮件
        // 发送HTML内容的邮件
        Message message = createHtmlMail(session);
       
        // 5. 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        ts.close();

    }

    /**
     * HTML内容邮件
     *      创建一封包含html内容的邮件
     */
    public static MimeMessage createHtmlMail(Session session) throws Exception {

        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的发件人的邮箱地址
        message.setFrom(new InternetAddress("发件人的邮箱地址"));
        // 设置邮件的收件人的邮箱地址 （现在发件人和收件人是一样的，那就是自己给自己发）
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("收件人的邮箱地址"));
        // 邮件的主题
        message.setSubject("测试HTML邮件");
        // 设置发送日期
        message.setSentDate(new Date());

        // 准备邮件数据
        /**
         * Message表示一个邮件，messgaes.getContent()返回一个Multipart对象。
         * 一个Multipart对象包含一个或多个BodyPart对象，来组成邮件的正文部分（包括附件）。
         */
        // 创建多媒体对象
        MimeMultipart multipart = new MimeMultipart();

        // 创建邮件体对象
        MimeBodyPart bodyPart = new MimeBodyPart();
        // 设置HTML内容
        StringBuffer sb = new StringBuffer();
        sb.append("<html><body><a href='http://www.baidu.com'>百度一下</a></body></html>");
        bodyPart.setContent(sb.toString(), "text/html;charset=UTF-8");

        // 将bodyPart对象设置到multipart对象中
        multipart.addBodyPart(bodyPart);
        // 将multipart对象设置到message对象中 （setContent()：）
        message.setContent(multipart);

        // 返回创建好的邮件对象
        return message;
    }   
}
```

效果如下：

![](/mail-06.png)



### 发送包含附件的邮件

```java
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
 * 发送包含附件的邮件
 */
public class MailTest03 {

    public static void main(String[] args) throws Exception {

        // 定义邮箱服务器配置
        Properties prop = new Properties();
        // 设置邮件服务器主机名 （163 邮件服务器地址："mail.smtp.host"  "smtp.163.com"）
        prop.setProperty("mail.smtp.host", "smtp.163.com");
        // 设置邮件服务器的端口
        prop.setProperty("mail.smtp.port", "25");
        // 设置邮件服务器认证属性 （设置为true表示发送服务器需要身份验证）
        prop.setProperty("mail.smtp.auth", "true");

        // 使用JavaMail发送邮件的5个步骤
        // 1. 创建session
        Session session = Session.getInstance(prop);
        // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        // 2. 通过session得到transport对象
        Transport ts = session.getTransport();
        // 3. 使用邮箱的用户名和密码连上邮件服务器（用户名只需写@前面的即可，密码是指授权码）
        ts.connect("smtp.163.com", "用户名", "密码");
        // 4. 创建邮件
        // 发送包含附件的邮件
        Message message = createAttachMail(session);
        
        // 5. 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        // 关闭transport对象
        ts.close();

    }

    /**
     * 包含附件的邮件
     *      创建一封包含附件的邮件
     * @param session
     * @return
     * @throws MessagingException
     */
    public static MimeMessage createAttachMail(Session session) throws MessagingException {
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 设置邮件的发件人的邮箱地址
        message.setFrom(new InternetAddress("发件人的邮箱地址"));
        // 设置邮件的收件人的邮箱地址 （现在发件人和收件人是一样的，那就是自己给自己发）
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("收件人的邮箱地址"));
        // 邮件的主题
        message.setSubject("测试包含附件的邮件");
        // 设置发送日期
        message.setSentDate(new Date());


        // 创建邮件正文
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent("使用JavaMail创建的带附件的邮件", "text/html;charset=UTF-8");

        // 创建附件
        MimeBodyPart attachPart = new MimeBodyPart();
        // 本地文件
        DataHandler dh = new DataHandler(new FileDataSource("C:\\work\\邮件附件.txt"));
        attachPart.setDataHandler(dh);
        // 附件名
        attachPart.setFileName(dh.getName());

        // 创建容器描述数据关系
        MimeMultipart multipart = new MimeMultipart();
        // 添加正文
        multipart.addBodyPart(bodyPart);
        // 添加附件
        multipart.addBodyPart(attachPart);
        // 如果在邮件中要添加附件，设置为mixed；。
        multipart.setSubType("mixed");

        // 设置邮件的内容
        message.setContent(multipart);

        // 返回创建好的邮件对象
        return message;
    }
}
```

效果如下：

![](/mail-07.png)



### Java Mail 邮件发送封装

#### 创建邮件发送信息类

```java
package com.xxxx.mail;

import java.util.List;

/**
 * 邮件发送信息类
 *   定义发送邮件时 邮件服务器 端口 发送方用户名 密码等字段
 */
public class MailSendInfo {

    private String serverHost; // 服务器主机
    private String serverPort; // 服务器端口

    private String fromAddress;// 发送方邮箱地址
    private List<String> toAddress; // 接收方邮箱地址

    private String userName; // 邮件服务器用户名
    private String userPwd; // 邮件服务器密码（授权密码）

    private String subject; // 邮件主题
    private String content; // 邮件内容

    private Boolean flag = true; // 是否需要身份认证

    private List<String> attachFileNames; // 附件文件名


    public Boolean getFlag() {
        return flag;
    }
    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
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
    public List<String> getToAddress() {
        return toAddress;
    }
    public void setToAddress(List<String> toAddress) {
        this.toAddress = toAddress;
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
    public List<String> getAttachFileNames() {
        return attachFileNames;
    }
    public void setAttachFileNames(List<String> attachFileNames) {
        this.attachFileNames = attachFileNames;
    }
}
```

#### 创建认证类

```java
package com.xxxx.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 认证类
 */
public class MyAuthenticator extends Authenticator {

    private String userName; // 邮箱
    private String userPwd; // 密码（授权码）
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
        return new PasswordAuthentication(userName, userPwd);
    }
}
```

#### 创建邮件发送器

```java
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
        try {
            // 定义邮箱服务器配置
            Properties props = new Properties();
            // 163 邮件服务器地址
            props.put("mail.smtp.host", mailSendInfo.getServerHost());
            // 163 邮件服务器端口
            props.put("mail.smtp.port",mailSendInfo.getServerPort());
            // 163 邮件服务器认证属性
            props.put("mail.smtp.auth", mailSendInfo.getFlag());
            // 身份认证类
            MyAuthenticator authenticator = new MyAuthenticator(mailSendInfo.getUserName(), mailSendInfo.getUserPwd());
            // 创建session
            session = Session.getDefaultInstance(props, authenticator);
            // 开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);
            // 创建message邮件对象
            message = new MimeMessage(session);
            // 设置发送方的邮箱地址
            Address from = new InternetAddress(mailSendInfo.getFromAddress());
            message.setFrom(from);
            // 设置发送时间
            message.setSentDate(new Date());

            // 判断接收方的邮箱地址
            if(null != mailSendInfo.getToAddress() && mailSendInfo.getToAddress().size() > 0){
                // 定义数组
                Address[] addresses = new Address[mailSendInfo.getToAddress().size()];
                // 循环获取接收方的邮箱地址，并设置到对应的address数组中
                for(int i = 0; i < mailSendInfo.getToAddress().size(); i++){
                    Address address = new InternetAddress(mailSendInfo.getToAddress().get(i));
                    addresses[i] = address;
                }
                // 设置接收方的邮箱地址
                message.setRecipients(Message.RecipientType.TO, addresses);
                // 设置邮件主题
                message.setSubject(mailSendInfo.getSubject());

                // 创建多媒体对象容器
                Multipart multipart = new MimeMultipart();

                // 创建正文内容
                BodyPart bodyPart =new MimeBodyPart();
                bodyPart.setContent(mailSendInfo.getContent(),"text/html;charset=utf-8");
                // 添加正文 （将正文内容设置到多媒体对象容器中）
                multipart.addBodyPart(bodyPart);

                // 获取所有的附件内容
                List<String> files = mailSendInfo.getAttachFileNames();
                // 判断是否包含附件内容
                if(null != files && files.size() > 0){
                    for(int i = 0; i < files.size(); i++){
                        // 获取对应的附件对象
                        File tempFile = new File(files.get(i));
                        // 判断附件是否存在
                        if(tempFile.exists()){
                            // 如果附件存在，创建附件对象
                            BodyPart attachPart = new MimeBodyPart();
                            attachPart.setDataHandler(new DataHandler(new FileDataSource(tempFile)));
                            // 设置文件名 （解决附件名乱码）
                            attachPart.setFileName(MimeUtility.encodeText(tempFile.getName()));
                            // 添加附件 （将附件内容添加到多媒体对象容器中）
                            multipart.addBodyPart(attachPart);
                        }
                    }
                }

                // 设置邮件内容
                message.setContent(multipart);
                // 发送邮件
                Transport.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

#### 邮件发送测试

##### 发送HTML内容的邮件

```java
/**
  * 发送HTML内容的邮件
  */
public static void testHtmlMail() {
    MailSendInfo mailSendInfo = new MailSendInfo();
    mailSendInfo.setServerHost("smtp.163.com");
    mailSendInfo.setServerPort("25");
    mailSendInfo.setUserName("邮箱用户名");
    mailSendInfo.setUserPwd("密码（授权码）");
    mailSendInfo.setSubject("邮件封装");
    mailSendInfo.setFromAddress("发件人的邮箱地址");
    mailSendInfo.setContent("<h2>这是封装后发送的邮件</h2>");
    List<String> users = new ArrayList<>();
    users.add("收件人的邮箱地址");
    mailSendInfo.setToAddress(users);
    MailSender mailSender=new MailSender();
    mailSender.sendMail(mailSendInfo);
}
```

效果如下：

![](/mail-08.png)

##### 发送包含附件的邮件

```java
/**
  * 发送包含附件的邮件
  */
public static void testAttachMail() {
    MailSendInfo mailSendInfo = new MailSendInfo();
    mailSendInfo.setServerHost("smtp.163.com");
    mailSendInfo.setServerPort("25");
    mailSendInfo.setUserName("用户名");
    mailSendInfo.setUserPwd("密码（授权码）");
    mailSendInfo.setSubject("邮件封装");
    mailSendInfo.setFromAddress("发件人的邮箱地址");
    mailSendInfo.setContent("<h2>包含附件的邮件</h2>");
    List<String> users = new ArrayList<>();
    users.add("收件人的邮箱地址");
    mailSendInfo.setToAddress(users);

    // 添加附件
    List<String> files=new ArrayList<String>();
    files.add("C:\\work\\邮件附件.txt");
    files.add("C:\\work\\名单.xlsx");
    mailSendInfo.setAttachFileNames(files);
    MailSender mailSender=new MailSender();
    mailSender.sendMail(mailSendInfo);
}
```

效果如下：

![](/mail-09.png)



## 使用 Spring API 实现邮件发送 

### 环境准备

创建 Maven 项目，在 pom.xml 文件中添加依赖

```xml
<!-- spring核心 jar 依赖 -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.4.RELEASE</version>
</dependency>
<!--spring 上下文环境 支持-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>5.2.4.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-test</artifactId>
    <version>5.2.4.RELEASE</version>
    <scope>test</scope>
</dependency>
<!-- Java Mail坐标依赖 -->
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>javax.mail</artifactId>
    <version>1.6.2</version>
</dependency>
```

### 配置邮件发送 bean 

在 spring.xml 配置文件中设置邮件发送对应的bean标签

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 开启自动化扫描  -->
    <context:component-scan base-package="com.xxxx"/>

    <!-- 邮件发送器（提供了邮件发送接口、透明创建Java Mail的MimeMessage、及邮件发送的配置） -->
    <bean id="mailSender" class="org.springframework.mail.javamail.JavaMailSenderImpl">
        <property name="host" value="smtp.163.com" />
        <property name="port" value="25" />
        <property name="defaultEncoding" value="utf-8"></property>
        <property name="username" value="用户名"></property>
        <property name="password" value="密码"></property>
    </bean>

    <!-- 普通文本邮件对象 -->
    <bean id="templateMessage" class="org.springframework.mail.SimpleMailMessage">
        <property name="from" value="发件人的邮箱地址" />
        <property name="subject" value="spring_mail" />
    </bean>

</beans>

```

### 定义接口与实现类 

定义接口

```java
package com.xxxx.springMail;

/**
 * 定义接口
 */
public interface OrderManager {
    void placeOrder();
}
```

定义实现类

```java
package com.xxxx.springMail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class SimpleOrderManager implements OrderManager {

    @Resource
    private JavaMailSenderImpl mailSender;
    @Resource
    private SimpleMailMessage templateMessage;

    @Override
    public void placeOrder() {
        // 获取邮件对象
        SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        // 设置邮件收件人的邮箱地址
        msg.setTo("收件人的邮箱地址");
        // 设置邮件内容
        msg.setText("Hello Spring Mail");
        try{
            // 发送邮件
            this.mailSender.send(msg);
        } catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
```

### 邮件发送测试

```java
/**
  *  发送邮件
  * @param args
  */
public static void main(String[] args) {
    ApplicationContext ac=new ClassPathXmlApplicationContext("spring.xml");
    SimpleOrderManager simpleOrderManager = 
        (SimpleOrderManager) ac.getBean("simpleOrderManager");
    simpleOrderManager.placeOrder();
}
```

### 发送附件

```java
/**
  *  发送包含附件的邮件
  * @param args
  * @throws MessagingException
  */
public static void main(String[] args) throws MessagingException {
    ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
    JavaMailSender mailSender= (JavaMailSender) ac.getBean("mailSender");
    MimeMessage message= mailSender.createMimeMessage();
    message.setSubject("spring_mail"); // 邮件主题
    // 创建带有附件的消息帮组类
    MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
    helper.setTo(new InternetAddress("收件人的邮箱地址"));//设置接收人
    helper.setText("Thank you for ordering!"); // 邮件内容
    helper.setFrom("发件人的邮箱地址"); // 发件人
    // 设置附件
    File file = new File("C:\\work\\邮件附件.txt");
    // 添加附件
    helper.addAttachment(file.getName(), file);
    // 发送邮件
    mailSender.send(message);
}
```



