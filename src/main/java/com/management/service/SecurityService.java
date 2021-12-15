package com.management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public String encodePassword(String password) {
		return bcryptPasswordEncoder.encode(password);
	}
	
	public boolean verifyPassword(String rawPassword, String hashedPassword) {
		return bcryptPasswordEncoder.matches(rawPassword, hashedPassword);
	}
}
