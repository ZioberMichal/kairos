package com.kairos.project.employees.web;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import com.kairos.project.employees.model.Employee;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Component
@AllArgsConstructor
public class EmployeeResponseProcessor {

	private final EmployeeMapper employeeMapper;

	EntityModel<EmployeeResponse> buildOne(Employee entity) {
		var response = employeeMapper.toResponse(entity);

		return addLinks(response);
	}

	CollectionModel<EntityModel<EmployeeResponse>> buildList(Collection<Employee> entities) {
		var responses = entities.stream().map(employeeMapper::toResponse).map(this::addLinks).toList();
		var models = CollectionModel.of(responses);
		models.add(linkTo(EmployeeController.class).withRel("create").withType(POST.name()));
		return models;
	}

	private EntityModel<EmployeeResponse> addLinks(EmployeeResponse response) {
		var model = EntityModel.of(response);
		model.add(linkTo(EmployeeController.class).slash(response.getId()).withSelfRel());
		model.add(linkTo(EmployeeController.class).slash(response.getId()).withRel("update").withType(POST.name()));
		model.add(linkTo(EmployeeController.class).slash(response.getId()).withRel("patch").withType(PATCH.name()));
		model.add(linkTo(EmployeeController.class).slash(response.getId()).withRel("delete").withType(DELETE.name()));

		return model;
	}
}
