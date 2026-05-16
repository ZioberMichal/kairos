package com.kairos.core.exceptions;

import lombok.Getter;
import com.kairos.core.web.model.FieldError;

import java.util.List;
import java.util.Objects;

@Getter
public abstract class DomainException extends RuntimeException {
	private final String messageKey;
	private final List<FieldError> errors;

	public DomainException(String message, String messageKey, List<FieldError> errors) {
		super(message);
		this.messageKey = Objects.requireNonNull(messageKey);
		this.errors = Objects.requireNonNull(errors);
	}

	public DomainException(String message, String messageKey) {
		this(message, messageKey, List.of());
	}
}
