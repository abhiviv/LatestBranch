package com.management.security;

import java.io.Serializable;

public class Jwtrequest implements Serializable {
	

	private static final long serialVersionUID = -7640990959716650241L;

	
	private String username;
	
	private String password;
	
	//need default constructor for JSON Parsing
	public Jwtrequest() {
			
	}
	
	public Jwtrequest(String username,String password) {
		this.username=username;
		this.password=password;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
