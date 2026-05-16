package com.kairos.project.departments.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByNameAndIdNot(String name, Long id);
}
