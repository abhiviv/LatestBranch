package com.management.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public final class SecurityUtils {
 
	public final MuserPrinciple getLoggedInUser() {
		return (MuserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
