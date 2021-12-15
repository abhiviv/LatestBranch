package com.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.management.service.MailService;
import com.management.service.OtpServices;


@RestController
@RequestMapping("/web/otp")
public class OtpController {

	
	@Autowired
	public OtpServices otpService;

	@Autowired
	public MailService mailService;

	@GetMapping(path = "/generateOtp/{EmailId}")
	public int generateOtp(@PathVariable("EmailId") String EmailId ) {
		String username = EmailId;
		int otp = otpService.generateOTP(username);
		mailService.sendOtp(EmailId, otp);
		return otp;
	}


	@RequestMapping(value ="/validateOtp/{EmailId}", method = RequestMethod.GET)
	public @ResponseBody ObjectNode validateOtp(@RequestParam("otpnum") int otpnum,@PathVariable("EmailId") String EmailId){
		ObjectMapper mapper  = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("SUCCESS", "Entered Otp is valid.");
		ObjectNode objectNode1 = mapper.createObjectNode();
		objectNode1.put("FAIL", "Entered Otp is NOT valid. Please Retry!");

		//Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = EmailId;

		//Validate the Otp 
		if(otpnum >= 0){
			int serverOtp = otpService.getOtp(username);
			if(serverOtp > 0){
				if(otpnum == serverOtp){
					otpService.clearOTP(username);
					return objectNode;
					
				}else{
					return objectNode1;
				}
			}else {
				return objectNode1;
			}
		}else {
			throw new com.management.exception.CustomeException("Otp not valid", "CheckOtp",
					"", "", "");
		}
	}


}
