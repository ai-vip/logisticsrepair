package com.logistics.common.utils.email;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {

    private String emailUser;
    private String emailPassword;

    public void initService(String emailUser, String emailPassword) {
        this.emailUser = emailUser;
        this.emailPassword = emailPassword;
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param toMail 发送邮箱
     * @param ccMails 抄送邮箱（多个以，分割）
     * @throws MessagingException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public void sendEmail(String subject, String content, String toMail, String ccMails) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.auth", "true");
        // 创建Session对象
        Session session = Session.getDefaultInstance(props
        // 以匿名内部类的形式创建登录服务器的认证对象
                , new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailUser, emailPassword);
                    }
                });

        Message message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(emailUser));

        // 设置收件人
        InternetAddress[] addresses = { new InternetAddress(toMail) };

        message.setRecipients(Message.RecipientType.TO, addresses);

        if (ccMails != null) {
            message.setRecipients(Message.RecipientType.CC, (InternetAddress[]) InternetAddress.parse(ccMails));
        }

        // 设置邮件主题
        message.setSubject(subject);
        // 构造Multipart
        Multipart mp = new MimeMultipart();
        // 向Multipart添加正文
        MimeBodyPart mbpContent = new MimeBodyPart();
        //mbpContent.setText(content);

        mbpContent.setContent(content, "text/html;charset=gb2312"); 
        // 将BodyPart添加到MultiPart中
        mp.addBodyPart(mbpContent);
        // 向Multipart添加MimeMessage
        message.setContent(mp);
        // 设置发送日期
        message.setSentDate(new Date());
        // 发送邮件
        Transport.send(message);
    }
    public void sendHtmlEmail(final String user,final String pwd,String subject, String content, String toMail, String ccMails) throws AddressException, MessagingException{
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.exmail.qq.com");
        props.put("mail.smtp.auth", "true");
        // 创建Session对象
        Session session = Session.getDefaultInstance(props
        // 以匿名内部类的形式创建登录服务器的认证对象
                , new Authenticator() {
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user, pwd);
                    }
                });

        Message message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(user));

        // 设置收件人
        InternetAddress[] addresses = { new InternetAddress(toMail) };

        message.setRecipients(Message.RecipientType.TO, addresses);

        if (ccMails != null) {
            message.setRecipients(Message.RecipientType.CC, (InternetAddress[]) InternetAddress.parse(ccMails));
        }

        // 设置邮件主题
        message.setSubject(subject);
        //指定邮箱内容及ContentType和编码方式
        message.setContent(content, "text/html;charset=utf-8");
        // 设置发送日期
        message.setSentDate(new Date());
        message.saveChanges();
        // 发送邮件
        Transport.send(message);
    }
    
    
}