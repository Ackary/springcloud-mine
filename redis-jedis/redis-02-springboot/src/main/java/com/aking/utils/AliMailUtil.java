package com.aking.utils;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * TODO
 *
 * @author ak
 * @version 1.0
 * @date 2021/5/18
 */
public class AliMailUtil {

    private static final String ALIDM_SMTP_HOST = "smtpdm.aliyun.com";

    private static final int ALIDM_SMTP_PORT = 25;

    /**
     * 发件人的账号
     */
    private final String user;

    /**
     * 发件人密码
     */
    private final String password;

    public AliMailUtil() {
        this("noreply@mail.appranking.com", "CDxiaoxi1994");
    }

    public AliMailUtil(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public static void main(String[] args) {
        String content = "<a href=\"http://localhost:8080/user/checkCode?code=" + 1111 + "\">激活请点击:" + 1111 + "</a>";

        new AliMailUtil().send("645771789@qq.com", "测试", content);
    }

    /**
     * 发送邮件
     *
     * @param toEmail 收件人邮箱地址
     * @param subject 邮件标题
     * @param content 邮件内容 可以是html内容
     */
    public void send(String toEmail, String subject, String content) {
        Session session = loadMailSession();

        // 创建邮件消息
        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发件人
            message.setFrom(new InternetAddress(user));
            Address[] a = new Address[1];
            a[0] = new InternetAddress(user);
            message.setReplyTo(a);

            // 设置收件人
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }
    }


    /**
     * 发送邮件 带附件
     *
     * @param toEmail    收件人邮箱地址
     * @param subject    邮件标题
     * @param content    邮件内容 可以是html内容
     * @param attachPath 附件路径
     */
    public void send(String toEmail, String subject, String content, String attachPath) {
        Session session = loadMailSession();

        MimeMessage mm = new MimeMessage(session);
        try {
            // 发件人
            mm.setFrom(new InternetAddress(user));
            // 收件人
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            // 设置抄送人
            //mm.setRecipient(Message.RecipientType.CC, new InternetAddress("XXXX@qq.com"));
            // 标题
            mm.setSubject(subject);
            //内容
            Multipart multipart = new MimeMultipart();
            // body部分
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            // 附件部分
            BodyPart attachPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(attachPath);
            attachPart.setDataHandler(new DataHandler(fileDataSource));
            attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
            multipart.addBodyPart(attachPart);

            mm.setContent(multipart);
            Transport.send(mm);
        } catch (Exception e) {
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }

    }

    private Session loadMailSession() {
        try {
            // 配置发送邮件的环境属性
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", ALIDM_SMTP_HOST);
            // props.put("mail.smtp.port", ALIDM_SMTP_PORT);
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.port", "465");
            // 发件人的账号
            props.put("mail.user", user);
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", password);
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            return Session.getInstance(props, authenticator);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mail session is null");
        }
        return null;
    }
}
