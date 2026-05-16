package com.kairos.core.exceptions;

import com.kairos.core.web.model.FieldError;

import java.util.List;
import java.util.Objects;

public class BadDataException extends DomainException {
	public BadDataException(String message, List<FieldError> errors) {
		super(message, "bad.data", Objects.requireNonNull(errors));
	}
}
