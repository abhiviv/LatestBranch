package com.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.management.entity.Muser;
import com.management.exception.CustomeException;
import com.management.repository.RegistrationRepository;

@Service
public class JWTUserDetailsService implements UserDetailsService{

	@Autowired
	private RegistrationRepository registrationRepository;
	
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		Muser muser=null;
		muser=registrationRepository.findByEmail(username);
		 if (muser == null) {
				throw new CustomeException("Check username",
				          "",
				          "Retry it",
				          "then click Submit",
				          "if and issued contact us");
			}
			return new MuserPrinciple(muser);
		
	}

}
