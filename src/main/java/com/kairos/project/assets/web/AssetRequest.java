package com.kairos.project.assets.web;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetRequest {

	@NotBlank(message = "not.blank")
	private String name;

	@NotBlank(message = "not.blank")
	private String assetNumber;
}
