package com.xjt.cloud.commons.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * 功能描述:发送邮件功能
 *
 * <dependency>
 * <groupId>org.glassfish</groupId>
 * <artifactId>javax.mail</artifactId>
 * <version>3.0-Prelude</version>
 * </dependency>
 */
public class MailUtils {

    /**
     * 发送普通电子邮件
     *
     * @param subject      标题(邮件标题)
     * @param body         正文(邮件内容)
     * @param personalName 发送方昵称
     * @param recipients   接收方列表(邮件地址)
     * @return success-成功; failure-失败
     */
    public static String send(String subject, String body, String personalName, String[] recipients) {
        try {
            // 获取系统环境
            Properties props = new Properties();

            // 进行邮件服务器用户认证
            Authenticator auth = new EmailAutherticator("jstxlog@btte.net", "3kbkxze3");
            props.put("mail.smtp.host", "mail.btte.net");
            props.put("mail.smtp.auth", "true");
            Session session = Session.getDefaultInstance(props, auth);
//			session.setDebug(true);

            // 设置session,与邮件服务器进行通讯
            Message message = new MimeMessage(session);

            // MiniMultipart类是一个容器类，包含MimeBodyPart类型对象
            Multipart mainPart = new MimeMultipart();

            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body, "text/html;charset=utf-8");
            mainPart.addBodyPart(bodyPart);

            // 将MimeMultipart对象设置为邮件内容
            message.setContent(mainPart);
            // 设置邮件格式
            message.setContent(body, "text/html;charset=utf-8");
            // 设置邮件主题
            message.setSubject(subject);
            // 设置邮件发送日期
            message.setSentDate(new Date());
            // 设置邮件发送者的地址
            Address address = new InternetAddress("jstxlog@btte.net", personalName);
            message.setFrom(address);

            // 设置邮件接收方的地址
            for (String recipient : recipients) {
                Address toAddress = new InternetAddress(recipient);
                message.addRecipient(Message.RecipientType.TO, toAddress);
            }

            // 发送邮件
            Transport.send(message);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "failure";
        }
    }

    /**
     * 用于服务器对用户的认证
     */
    public static class EmailAutherticator extends Authenticator {
        public String authUsr;
        public String authPwd;

        public EmailAutherticator(String authUsr, String authPwd) {
            this.authUsr = authUsr;
            this.authPwd = authPwd;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(authUsr, authPwd);
        }
    }

    public static void main(String[] args) {
        String subject = "测试邮件发送功能";
        String body = "<html><body><table border=\"1\"><tr><td>类型</td><td>数量</td></tr><tr><td>失败</td><td>1</td></tr></table></body></html>";
        String personalName = "消检通系统";
        String[] recipients = new String[]{"huanggc@xiaojiantong.com", "wangzw@xiaojiantong.com"};
        String response = MailUtils.send(subject, body, personalName, recipients);
        System.out.println(response);
    }

}
