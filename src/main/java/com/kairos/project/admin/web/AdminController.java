package com.kairos.project.admin.web;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kairos.project.admin.model.AdminService;

import java.util.Map;

import static com.kairos.core.web.ApiConstants.Admin.ADMIN_API;
import static com.kairos.core.web.ApiConstants.Admin.ME;
import static com.kairos.core.web.ApiConstants.Admin.Perms.ADMIN;

@RestController
@RequestMapping(value = ADMIN_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping(ME)
	@PreAuthorize("hasAuthority('" + ADMIN + "')")
	public EntityModel<Map<String, String>> getMe() {
		return EntityModel.of(Map.of("me", adminService.getMe()));
	}
}
