package com.management.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.management.security.Constants.EXPIRATION_TIME;
import static com.management.security.Constants.HEADER_STRING;
import static com.management.security.Constants.SECRET;
import static com.management.security.Constants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.entity.LoginDto;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
 
	 private AuthenticationManager authenticationManager;
	    
	    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }

	    public JWTAuthenticationFilter(String loginProcessingUrl, AuthenticationManager authenticationManager) {
	    	setFilterProcessesUrl(loginProcessingUrl);
	        this.authenticationManager = authenticationManager;
	    }
	     
	    
	    
	    @Override
	    public Authentication attemptAuthentication(HttpServletRequest req,
	                                                HttpServletResponse res) throws AuthenticationException {
	        try {
	            LoginDto creds = new ObjectMapper()
	                    .readValue(req.getInputStream(), LoginDto.class);

	            return authenticationManager.authenticate(
	                    new UsernamePasswordAuthenticationToken(
	                            creds.getUsername(),
	                            creds.getPassword(),
	                            new ArrayList<>())
	            );
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    @Override
	    protected void successfulAuthentication(HttpServletRequest req,
	                                            HttpServletResponse res,
	                                            FilterChain chain,
	                                            Authentication auth) throws IOException, ServletException {

	    	// get the roles from authentication object and set it in token
	    	// same roles would be used until user logout and login again
	    	List<String> roles = auth.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
	    	System.out.println(roles);
	    	
	        String token = JWT.create()
	                .withSubject(((MuserPrinciple)auth.getPrincipal()).getUsername())
	                .withClaim("roles", roles)
	                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
	                .sign(HMAC512(SECRET.getBytes()));
	        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	        res.setHeader("Content-Type", "application/json;charset=UTF-8");
	        System.out.println("--------------------"+token);
	        res.getOutputStream().print("{\"token\":" + "\"" + token + "\"}");
	    }

}
