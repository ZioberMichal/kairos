package com.kairos.core.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TimeMapper {

	DateTimeFormatter LOCAL_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	default String formatLocalDateTime(LocalDateTime value) {
		if (value == null) {
			return null;
		}

		return LOCAL_DATE_TIME_FORMATTER.format(value);
	}
}
