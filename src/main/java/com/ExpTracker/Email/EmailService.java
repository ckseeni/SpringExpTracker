package com.ExpTracker.Email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailService {

	public void sendEmail(String expCSVData, String username) throws MessagingException, IOException {
		
		String host="smtp.gmail.com";  
		final String user="ckseenitce@gmail.com";  
		final String password="thewalkingdead";
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
		message.setSubject("ExpensesList");  
		
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText("Hi "+username+", Expenses list is provided as attachment. Download it");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        messageBodyPart = new MimeBodyPart();
        DataSource source = new ByteArrayDataSource(expCSVData, "application/x-any");
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName("ExpensesList.csv");
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);
		         
		Transport.send(message);  
		   
	}  
	
}
