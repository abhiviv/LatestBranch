package com.management.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@Transactional
public class MailService  {

	private JavaMailSender javaMailSender;
	
	private SpringTemplateEngine templateEngine;
	
	   @Autowired
	    public MailService(JavaMailSender javaMailSender,SpringTemplateEngine templateEngine) {
	        this.javaMailSender = javaMailSender;
	        this.templateEngine=templateEngine;
	    }

	   
	   public void sendSimpleMessage(String to, String subject, String text) {
		   try {

		        MimeMessage message = javaMailSender.createMimeMessage();
		        
		        final Context ctx = new Context();
		        ctx.setVariable("username", text);

		        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom("info@gmail.com");
		        helper.setTo(to);
		        helper.setSubject(subject);
		        final String htmlContent = this.templateEngine.process("Registration.html", ctx);
		        message.setText(htmlContent,  "utf-8", "html"); // true = isHtml

		        javaMailSender.send(message);

		    } catch (MessagingException ex) {
		    	
		    }
			    }
	   
	   
	   public void sendOtp(String to, int otp) {
		   try {

		        MimeMessage message = javaMailSender.createMimeMessage();
		        
		        final Context ctx = new Context();
		        ctx.setVariable("otp", otp);

		        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
				helper.setFrom("info@gmail.com");
		        helper.setTo(to);
		        helper.setSubject("Verification Code");
		        final String htmlContent = this.templateEngine.process("otp.html", ctx);
		        message.setText(htmlContent,  "utf-8", "html"); // true = isHtml

		        javaMailSender.send(message);

		    } catch (MessagingException ex) {
		    	
		    }
			    }

}
