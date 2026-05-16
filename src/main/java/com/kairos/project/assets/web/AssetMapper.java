package com.kairos.project.assets.web;

import com.kairos.core.mapstruct.OptionalMapper;
import com.kairos.project.assets.model.Asset;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OptionalMapper.class)
public abstract class AssetMapper {

	abstract Asset fromRequest(AssetRequest request);

	abstract void updateRequest(AssetRequest request, @MappingTarget Asset asset);

	@BeanMapping(nullValueCheckStrategy = ALWAYS, nullValuePropertyMappingStrategy = IGNORE)
	abstract void patchRequest(AssetPatchRequest request, @MappingTarget Asset asset);

	abstract AssetResponse toResponse(Asset asset);
}
