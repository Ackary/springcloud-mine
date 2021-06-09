package com.aking.controller;

import com.aking.service.IMailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * MailController
 *
 * @author ak
 * @version 1.0
 * @date 2021/5/18
 */
@RequestMapping("/mail")
@RestController
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private IMailService mailService;

    @GetMapping("/serviceMail")
    public void serviceMail() {
        String subject = "App ranking";
        String context = "<a href=\"http://localhost:8080/user/checkCode?code=" + 1111 + "\">激活请点击:" + 1111 + "</a>";
        //发送激活邮件
        mailService.sendHtmlMail("645771789@qq.com", subject, context);
    }

    @GetMapping
    public void sendMail() {
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("这是一封测试邮件");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("3557201370@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        // message.setTo("10*****16@qq.com","12****32*qq.com");
        message.setTo("645771789@qq.com");
        // 设置邮件抄送人，可以有多个抄送人
        //message.setCc("12****32*qq.com");
        // 设置隐秘抄送人，可以有多个
        //message.setBcc("7******9@qq.com");
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("这是测试邮件的正文");
        // 发送邮件
        javaMailSender.send(message);
    }
}
