package com.kairos.core.web.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
public class ApiError {
	HttpStatus status;
	int statusCode;
	String messageKey;
	List<FieldError> errors;
	String message;
}
