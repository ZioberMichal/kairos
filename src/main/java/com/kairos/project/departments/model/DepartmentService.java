package com.kairos.project.departments.model;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.kairos.core.exceptions.Exceptions.badData;
import static com.kairos.core.exceptions.Exceptions.notFound;
import static com.kairos.core.exceptions.Validate.ifTrue;
import static com.kairos.core.web.model.FieldError.alreadyExists;

@Service
@RequiredArgsConstructor
public class DepartmentService {

	private final DepartmentRepository departmentRepository;

	public List<Department> listAll() {
		return departmentRepository.findAll(Sort.by(Sort.Order.asc("name")));
	}

	public Department getById(Long id) {
		return departmentRepository.findById(id).orElseThrow(() -> notFound(id));
	}

	public Department save(Department department) {
		validate(department);
		return departmentRepository.save(department);
	}

	public void delete(Long id) {
		departmentRepository.deleteById(id);
	}

	void validate(Department department) {
		final var departmentIdOrNonExistent = Objects.requireNonNullElse(department.getId(), 0L);
		ifTrue(departmentRepository.existsByNameAndIdNot(department.getName(), departmentIdOrNonExistent))
				.fail(() -> badData("Name already exists!", alreadyExists("name", department.getName())));
	}
}
