package com.kairos.core.web.model;

import lombok.Builder;
import lombok.Data;

import static com.kairos.core.web.model.ErrorKeys.ALREADY_EXISTS_KEY;
import static com.kairos.core.web.model.ErrorKeys.UNKNOWN_KEY;

@Data
@Builder
public class FieldError {
	String field;
	Object value;
	String messageKey;

	public static FieldErrorBuilder ofName(String name) {
		return FieldError.builder().field(name);
	}

	public static FieldError alreadyExists(String fieldName, Object fieldValue) {
		return FieldError.ofName(fieldName).value(fieldValue).messageKey(ALREADY_EXISTS_KEY).build();
	}

	public static FieldError unknown(String fieldName, Object fieldValue) {
		return FieldError.ofName(fieldName).value(fieldValue).messageKey(UNKNOWN_KEY).build();
	}
}
