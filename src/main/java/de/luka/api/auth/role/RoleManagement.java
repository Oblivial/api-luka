package de.luka.api.auth.role;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("unchecked")

public class RoleManagement{
	
	private static SecurityContext context;
	private static Authentication auth;
	private static String username;



	private static List<String> credentials;
	
	public RoleManagement() {throw new IllegalStateException("Cannot create instance of purely static Class");}
	
	
	static {
		initialize();
	}
	
	
	private static void initialize() {
		context = SecurityContextHolder.getContext();
		auth = context.getAuthentication();
		username = auth.getName();
		credentials = null;
		if(auth.getCredentials()!= null && auth.getCredentials() instanceof List) {
			List<?> genericCredentialsList = (List<?>) auth.getCredentials();
			if(!genericCredentialsList.isEmpty() && genericCredentialsList.get(0) instanceof String) {
				credentials = (List<String>) genericCredentialsList;
			}
		}
		
	}
	
	public static String getAuthUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static boolean userHasRole(String roleName) {
		return credentials.contains("ROLE_" + roleName);
	}
}
