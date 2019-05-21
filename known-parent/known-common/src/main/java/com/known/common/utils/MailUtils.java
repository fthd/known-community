package com.known.common.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 * javaMail发送邮件工具类
 * 
 */
public class MailUtils {
        /**
         * 发送邮件
         * 
         * @param toAddress
         *            接收邮件地址
         * @param subject
         *            邮件主题
         * @param content
         *            邮件内容
         * @throws Exception 
         */
        public static void sendMail(final String sendUserName,
                        final String sendPassword, String toAddress, String subject, String content) throws Exception {
        	Properties p = new Properties();
    		//p.setProperty("mail.host", "smtp.sina.com");
			p.setProperty("mail.transport.protocol", "smtp");
    		p.setProperty("mail.smtp.host", "smtp.exmail.qq.com");
			p.setProperty("mail.smtp.port", "465");
    		p.setProperty("mail.smtp.auth", "true");
			MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
			} catch (GeneralSecurityException e1) {
				e1.printStackTrace();
			}
			p.put("mail.smtp.ssl.enable", "true");
			p.put("mail.smtp.ssl.socketFactory", sf);
    		Authenticator auth = new Authenticator(){
    			@Override
    			protected PasswordAuthentication getPasswordAuthentication() {
    				return new PasswordAuthentication(sendUserName,sendPassword);
    			}
    		};
    		Session session = Session.getDefaultInstance(p, auth);
    		MimeMessage mm = new MimeMessage(session);
			mm.setFrom(new InternetAddress(sendUserName));
			mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			mm.setSubject(subject);
			mm.setContent(content, "text/html;charset=utf-8");
			Transport.send(mm);
        }
}

