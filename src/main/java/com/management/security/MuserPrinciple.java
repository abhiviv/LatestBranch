package com.management.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.management.entity.Muser;

public class MuserPrinciple implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Muser mUser;
	
	public MuserPrinciple(Muser muser) {
		this.mUser=muser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return getGrantedAuthorities(Arrays.asList(mUser.getRole()));
	}
	//method
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges){
		List<GrantedAuthority>authorities = new ArrayList<>();
		for(String privilege:privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return mUser.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return mUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
