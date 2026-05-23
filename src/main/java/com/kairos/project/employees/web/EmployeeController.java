package com.kairos.project.employees.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kairos.core.web.model.ApiError;
import com.kairos.project.employees.model.EmployeeService;

import static com.kairos.core.web.ApiConstants.Employees.EMPLOYEES_API;
import static com.kairos.core.web.ApiConstants.Employees.Perms.EMPLOYEES_READ;

@RestController
@RequestMapping(value = EMPLOYEES_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Endpoints for browsing employee information")
public class EmployeeController {

	private final EmployeeService employeeService;

	private final EmployeeResponseProcessor employeeResponseProcessor;

	@GetMapping
	@PreAuthorize("hasAuthority('" + EMPLOYEES_READ + "')")
	@Operation(
			summary = "Get all employees",
			description = "Retrieves the complete list of employees available to the authenticated user."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Employees were successfully retrieved",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = EmployeeResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "401",
					description = "Authentication is required to access employee information",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to read employees",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public CollectionModel<EntityModel<EmployeeResponse>> getAll() {
		var employees = employeeService.listAll();
		return employeeResponseProcessor.buildList(employees);
	}

}
