package com.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Mailthread extends Thread{
	
	@Autowired
	private MailService mailService;
	 
    private static final Logger logger = LoggerFactory.getLogger(Mailthread.class);
	
	private String Email;
	
	private String Name;
	

	
	public Mailthread(MailService mailService, String email, String name) {
		this.mailService = mailService;
		Email = email;
		Name = name;
	}



	@Override
	public synchronized void run() {
		 try {
			Thread.sleep(1000);
			logger.debug("thread Started");
			mailService.sendSimpleMessage(Email, "Register Sucessfully",
					"Welcome " + Name);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("error"+e);
		}
		
		
	}






	
	
}
