package com.kairos.project.employees.web;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.kairos.core.mapstruct.OptionalMapper;
import com.kairos.project.employees.model.Employee;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OptionalMapper.class)
public abstract class EmployeeMapper {

	abstract EmployeeResponse toResponse(Employee employee);

}
