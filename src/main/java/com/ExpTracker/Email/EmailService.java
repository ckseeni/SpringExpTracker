package com.ExpTracker.Email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

	public void sendEmail() throws MessagingException {
		
		String host="smtp.gmail.com";  
		final String user="ckseenitce@gmail.com";  
		final String password="115690433";
		final String port = "465";    
		String to="ckseeni199631@gmail.com";
		    
		Properties props = new Properties();  
		props.put("mail.smtp.host",host);  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		   
		Session session = Session.getDefaultInstance(props,  
			new javax.mail.Authenticator() {  
		    	protected PasswordAuthentication getPasswordAuthentication() {  
		    		return new PasswordAuthentication(user,password);  
		      }  
			});  
		  
		MimeMessage message = new MimeMessage(session);  
		message.setFrom(new InternetAddress(user));  
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		message.setSubject("test");  
		message.setText("test mail");  
		         
		Transport.send(message);  
		   
	}  
	
}
