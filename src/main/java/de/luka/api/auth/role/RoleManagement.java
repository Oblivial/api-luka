package de.luka.api.auth.role;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

public class RoleManagement {
	public static String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public static boolean userHasRole(String roleName) {
		if(SecurityContextHolder.getContext().getAuthentication() != null) {
			List<String> roles = (List) SecurityContextHolder.getContext().getAuthentication().getCredentials();
			return roles.contains("ROLE_" + roleName);
		} else {
			throw new RuntimeException("Authentication failure");
		}
	}
}
