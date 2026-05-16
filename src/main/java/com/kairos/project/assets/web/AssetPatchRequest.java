package com.kairos.project.assets.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Optional;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetPatchRequest {

	private Optional<@NotBlank String> name;
	private Optional<@NotBlank String> assetNumber;
	private Optional<@Min(1) Long> location;
}
