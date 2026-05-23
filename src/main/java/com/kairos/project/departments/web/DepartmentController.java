package com.kairos.project.departments.web;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import com.kairos.core.web.model.ApiError;
import com.kairos.project.departments.model.DepartmentService;

import static com.kairos.core.web.ApiConstants.Departments.DEPARTMENTS_API;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_DELETE;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_READ;
import static com.kairos.core.web.ApiConstants.Departments.Perms.DEPARTMENTS_UPDATE;
import static com.kairos.core.web.ApiConstants.PARAM_ID;

@RestController
@RequestMapping(value = DEPARTMENTS_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Endpoints for browsing, creating, updating, patching, and deleting departments")
public class DepartmentController {

	private final DepartmentService departmentService;
	private final DepartmentMapper departmentMapper;
	private final DepartmentResponseProcessor departmentResponseProcessor;

	@GetMapping
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_READ + "')")
	@Operation(
			summary = "Get all departments",
			description = "Retrieves the complete list of departments available to the authenticated user."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Departments were successfully retrieved",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = DepartmentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to read departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public CollectionModel<EntityModel<DepartmentResponse>> getAll() {
		var departments = departmentService.listAll();
		return departmentResponseProcessor.buildList(departments);
	}

	@GetMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_READ + "')")
	@Operation(
			summary = "Get department by ID",
			description = "Retrieves a single department by its unique identifier."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Department was successfully retrieved",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = DepartmentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to read departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Department was not found",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public EntityModel<DepartmentResponse> getById(
			@Parameter(description = "Unique identifier of the department", example = "1", required = true)
			@PathVariable Long id) {
		var department = departmentService.getById(id);
		return departmentResponseProcessor.buildOne(department);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	@Operation(
			summary = "Create department",
			description = "Creates a new department using the provided department details."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Department was successfully created",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = DepartmentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Request body is invalid",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to create departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public EntityModel<DepartmentResponse> createNew(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Department data used to create a new department",
					required = true,
					content = @Content(schema = @Schema(implementation = DepartmentRequest.class))
			)
			@Valid @RequestBody DepartmentRequest request) {
		var department = departmentMapper.fromRequest(request);
		var savedDepartment = departmentService.save(department);
		return departmentResponseProcessor.buildOne(savedDepartment);
	}

	@PostMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	@Operation(
			summary = "Update department",
			description = "Updates an existing department by replacing its editable fields with the provided department details."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Department was successfully updated",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = DepartmentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Request body is invalid",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to update departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Department was not found",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public EntityModel<DepartmentResponse> update(
			@Parameter(description = "Unique identifier of the department to update", example = "1", required = true)
			@PathVariable Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Department data used to replace editable department fields",
					required = true,
					content = @Content(schema = @Schema(implementation = DepartmentRequest.class))
			)
			@Valid @RequestBody DepartmentRequest request) {
		var existing = departmentService.getById(id);
		departmentMapper.updateRequest(request, existing);
		var updated = departmentService.save(existing);
		return departmentResponseProcessor.buildOne(updated);
	}

	@PatchMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_UPDATE + "')")
	@Operation(
			summary = "Patch department",
			description = "Partially updates an existing department using only the fields provided in the request body."
	)
	@ApiResponses({
			@ApiResponse(
					responseCode = "200",
					description = "Department was successfully patched",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = DepartmentResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Request body is invalid",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to update departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Department was not found",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public EntityModel<DepartmentResponse> patch(
			@Parameter(description = "Unique identifier of the department to patch", example = "1", required = true)
			@PathVariable Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Partial department data to apply",
					required = true,
					content = @Content(schema = @Schema(implementation = DepartmentPatchRequest.class))
			)
			@Valid @RequestBody DepartmentPatchRequest request) {
		var existing = departmentService.getById(id);
		departmentMapper.patchRequest(request, existing);
		var updated = departmentService.save(existing);
		return departmentResponseProcessor.buildOne(updated);
	}

	@DeleteMapping(PARAM_ID)
	@PreAuthorize("hasAuthority('" + DEPARTMENTS_DELETE + "')")
	@Operation(
			summary = "Delete department",
			description = "Deletes an existing department by its unique identifier."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Department was successfully deleted"),
			@ApiResponse(
					responseCode = "403",
					description = "The authenticated user does not have permission to delete departments",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Department was not found",
					content = @Content(schema = @Schema(implementation = ApiError.class))
			)
	})
	public ResponseEntity<Void> delete(
			@Parameter(description = "Unique identifier of the department to delete", example = "1", required = true)
			@PathVariable Long id) {
		departmentService.delete(id);

		return ResponseEntity.ok().build();
	}
}
