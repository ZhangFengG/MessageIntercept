package com.vito.messageintercept.Util;


import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @Description: TODO
 * @Created by Zhangfeng on 2016/5/28.
 * @ModifiedBy: Clowire51
 * @ModifiedTime: 2016/5/28 14:04
 * @ModifiedNotes:
 * @Version
 */
public class EmailUtil {

    class MyAuthenticator extends javax.mail.Authenticator {
        private String strUser;
        private String strPwd;

        public MyAuthenticator(String user, String password) {
            this.strUser = user;
            this.strPwd = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(strUser, strPwd);
        }
    }


    /**
     * @param toMail:是发送目的的邮箱。
     * @param fromMail:是用于发送的邮箱。
     * @param server:固定写为”smtp.mxhichina.com”。这是阿里云企业邮箱地址。
     * @param username:指用于发送邮件的邮箱账号。
     * @param password:指用于发送邮件的邮箱密码。
     * @param title:邮件的题目和。
     * @param body:内容
     * @param attachment:表示上传的附件在手机中的路径。
     * @description: Created by Zhangfeng on 2016/5/28 14:33
     */
    public void sendMail(String toMail, String fromMail, String server,
                         String username, String password, String title, String body,
                         String attachment) throws Exception {

        Properties props = System.getProperties();// Get system properties
        //添加邮箱地址。
        props.put("mail.smtp.host", server);// Setup mail server

        props.put("mail.smtp.auth", "true");
        //添加邮箱权限
        MyAuthenticator myauth = new MyAuthenticator(username, password);// Get

        Session session = Session.getDefaultInstance(props, myauth);

        MimeMessage message = new MimeMessage(session); // Define message
        //设置目的邮箱
        message.setFrom(new InternetAddress(fromMail)); // Set the from address

        message.addRecipient(Message.RecipientType.TO, new InternetAddress(
                toMail));// Set
        //设置邮件的标题
        message.setSubject(title);// Set the subject

        // message.setText(MimeUtility.encodeWord(body));// Set the content

        MimeMultipart allMultipart = new MimeMultipart("mixed");

        //添加附件
        if(attachment!=null){
            MimeBodyPart attachPart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            attachPart.setDataHandler(new DataHandler(fds));//附件
            attachPart.setFileName(MimeUtility.encodeWord(fds.getName()));
            allMultipart.addBodyPart(attachPart);
        }

        MimeBodyPart textBodyPart = new MimeBodyPart();
        //添加邮件内容
        textBodyPart.setText(body);


        allMultipart.addBodyPart(textBodyPart);
        message.setContent(allMultipart);
        message.saveChanges();
        Transport.send(message);//发送邮件
    }

    public void sendMailBy163(String toMail,String username, String password, String title, String body,
                         String attachment) throws Exception {
        sendMail(toMail,"clowiretest@163.com", "smtp.163.com", username, password, title, body, attachment);
    }
}
