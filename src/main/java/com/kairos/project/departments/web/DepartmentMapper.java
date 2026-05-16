package com.kairos.project.departments.web;

import org.mapstruct.*;
import com.kairos.core.mapstruct.OptionalMapper;
import com.kairos.project.departments.model.Department;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OptionalMapper.class)
public abstract class DepartmentMapper {

    abstract Department fromRequest(DepartmentRequest request);

    abstract void updateRequest(DepartmentRequest request, @MappingTarget Department department);

    @BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
    abstract void patchRequest(DepartmentPatchRequest request, @MappingTarget Department department);

    abstract DepartmentResponse toResponse(Department department);

}
