package com.kairos.project.departments.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DepartmentPatchRequest {

    private Optional<@NotBlank String> name;
}
