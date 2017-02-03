package de.rhyznr.vaadin.security;

import org.springframework.security.core.Authentication;

public class SecurityUtils {
	public static Account getPrincipal(Authentication authentication) {
		return (Account) authentication.getPrincipal();
	}

	public static boolean hasRole(Authentication authentication, String role) {
		return getPrincipal(authentication).getAuthorities().stream().anyMatch(authority -> authority.getName().equals(role));
	}
}
