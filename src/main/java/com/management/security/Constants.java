package com.management.security;

public class Constants{

	    public static final String SECRET = "SecretKeyToGenJWTs";
	    
	    public static final long EXPIRATION_TIME = 1800_000; // 10 days
	    
	    public static final String TOKEN_PREFIX = "Bearer ";
	    
	    public static final String HEADER_STRING = "Authorization";
	    
	    public static final String LOGIN_URL = "/web/authenticate";
	    
}
