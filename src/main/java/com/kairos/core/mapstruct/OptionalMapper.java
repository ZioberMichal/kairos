package com.kairos.core.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Optional;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OptionalMapper {
	default String optionalToString(Optional<String> value) {
		return value == null ? null : value.orElse(null);
	}

	default Integer optionalToInteger(Optional<Integer> value) {
		return value == null ? null : value.orElse(null);
	}

	default Long optionalToLong(Optional<Long> value) {
		return value == null ? null : value.orElse(null);
	}

	default Boolean optionalToBoolean(Optional<Boolean> value) {
		return value == null ? null : value.orElse(null);
	}
}
