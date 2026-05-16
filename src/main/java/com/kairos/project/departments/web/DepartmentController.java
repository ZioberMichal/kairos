package com.kairos.project.departments.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kairos.project.departments.model.DepartmentService;

import static com.kairos.core.web.ApiConstants.Departments.DEPARTMENTS_API;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_DELETE;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_READ;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_UPDATE;
import static com.kairos.core.web.ApiConstants.PARAM_ID;

@RestController
@RequestMapping(value = DEPARTMENTS_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class DepartmentController {

	private final DepartmentService departmentService;
	private final DepartmentMapper departmentMapper;
	private final DepartmentResponseProcessor departmentResponseProcessor;

	@GetMapping
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_READ + "')")
	public CollectionModel<EntityModel<DepartmentResponse>> getAll() {
		var departments = departmentService.listAll();
		return departmentResponseProcessor.buildList(departments);
	}

	@GetMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_READ + "')")
	public EntityModel<DepartmentResponse> getById(@PathVariable Long id) {
		var department = departmentService.getById(id);
		return departmentResponseProcessor.buildOne(department);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	public EntityModel<DepartmentResponse> createNew(@Valid @RequestBody DepartmentRequest request) {
		var department = departmentMapper.fromRequest(request);
		var savedDepartment = departmentService.save(department);
		return departmentResponseProcessor.buildOne(savedDepartment);
	}

	@PostMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	public EntityModel<DepartmentResponse> update(@PathVariable Long id, @Valid @RequestBody DepartmentRequest request) {
		var existing = departmentService.getById(id);
		departmentMapper.updateRequest(request, existing);
		var updated = departmentService.save(existing);
		return departmentResponseProcessor.buildOne(updated);
	}

	@PatchMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	public EntityModel<DepartmentResponse> patch(@PathVariable Long id, @Valid @RequestBody DepartmentPatchRequest request) {
		var existing = departmentService.getById(id);
		departmentMapper.patchRequest(request, existing);
		var updated = departmentService.save(existing);
		return departmentResponseProcessor.buildOne(updated);
	}

	@DeleteMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_DELETE + "')")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		departmentService.delete(id);

		return ResponseEntity.ok().build();
	}
}
