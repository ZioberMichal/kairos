package com.kairos.project.admin.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

	public String getMe() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
