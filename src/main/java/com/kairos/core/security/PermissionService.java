package com.kairos.core.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionService {

	public boolean hasPermission(String permission) {
		final var auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getAuthorities() == null) {
			return false;
		}
		return auth.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(permission));
	}
}
