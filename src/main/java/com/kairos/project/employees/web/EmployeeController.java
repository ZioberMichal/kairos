package com.kairos.project.employees.web;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kairos.project.employees.model.EmployeeService;

import static com.kairos.core.web.ApiConstants.Employees.EMPLOYEES_API;
import static com.kairos.core.web.ApiConstants.Employees.Perms.EMPLOYEES_READ;

@RestController
@RequestMapping(value = EMPLOYEES_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	private final EmployeeResponseProcessor employeeResponseProcessor;

	@GetMapping
	@PreAuthorize("hasAuthority('" + EMPLOYEES_READ + "')")
	public CollectionModel<EntityModel<EmployeeResponse>> getAll() {
		var employees = employeeService.listAll();
		return employeeResponseProcessor.buildList(employees);
	}

}
