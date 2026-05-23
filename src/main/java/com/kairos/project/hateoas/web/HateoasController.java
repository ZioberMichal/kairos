package com.kairos.project.hateoas.web;

import com.kairos.core.security.PermissionService;
import com.kairos.core.security.SecurityUtils;
import com.kairos.core.web.ApiConstants.*;
import com.kairos.project.admin.web.AdminController;
import com.kairos.project.assets.web.AssetController;
import com.kairos.project.departments.web.DepartmentController;
import com.kairos.project.employees.web.EmployeeController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.kairos.core.web.ApiConstants.Hateoas.HATEOAS_API;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = HATEOAS_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "HATEOAS", description = "Endpoint for discovering permission-based API links")
public class HateoasController {

	private final PermissionService permissionService;

	private final Map<Class<?>, Class<?>> controllers = Map.of(
			AdminController.class, Admin.Perms.class,
			AssetController.class, Assets.Perms.class,
			DepartmentController.class, Departments.Perms.class,
			EmployeeController.class, Employees.Perms.class
	);

	@GetMapping
	@Operation(
			summary = "Get API links",
			description = "Returns HATEOAS links available to the authenticated user based on their permissions."
	)
	public EntityModel<Object> getHateoas() {
		var model = EntityModel.of(new Object());
		addDefaultLinks(model);
		addCustomLinks(model);

		return model;
	}

	private void addDefaultLinks(EntityModel<Object> model) {
		controllers.forEach((controller, perms) -> {
			var linkBuilder = linkTo(controller);
			SecurityUtils.findPermsMapping(perms).forEach((method, permission) -> {
				if (permissionService.hasPermission(permission)) {
					final var methodName = method.name();
					model.add(linkBuilder.withRel(toRel(controller, methodName)).withType(methodName));
				}
			});
		});
	}

	private void addCustomLinks(EntityModel<Object> model) {
		// some permission-based links
	}

	private String toRel(Class<?> controller, String methodName) {
		final var controllerName = controller.getSimpleName().replace(Controller.class.getSimpleName(), "");
		return StringUtils.uncapitalize(controllerName) + methodName;
	}
}
