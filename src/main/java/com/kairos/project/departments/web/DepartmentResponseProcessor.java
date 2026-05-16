package com.kairos.project.departments.web;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import com.kairos.project.departments.model.Department;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Component
@AllArgsConstructor
public class DepartmentResponseProcessor {

    private final DepartmentMapper departmentMapper;

    EntityModel<DepartmentResponse> buildOne(Department entity) {
        var response = departmentMapper.toResponse(entity);

        return addLinks(response);
    }

    CollectionModel<EntityModel<DepartmentResponse>> buildList(Collection<Department> entities) {
        var responses = entities.stream().map(departmentMapper::toResponse).map(this::addLinks).toList();
        var models = CollectionModel.of(responses);
        models.add(linkTo(DepartmentController.class).withRel("create").withType(POST.name()));

        return models;
    }

    private EntityModel<DepartmentResponse> addLinks(DepartmentResponse response) {
        var model = EntityModel.of(response);
        model.add(linkTo(DepartmentController.class).slash(response.getId()).withSelfRel());
        model.add(linkTo(DepartmentController.class).slash(response.getId()).withRel("update").withType(POST.name()));
        model.add(linkTo(DepartmentController.class).slash(response.getId()).withRel("patch").withType(PATCH.name()));
        model.add(linkTo(DepartmentController.class).slash(response.getId()).withRel("delete").withType(DELETE.name()));

        return model;
    }
}
