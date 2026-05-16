package com.kairos.core.exceptions;

public class BadRequestException extends DomainException {
	public BadRequestException(String message, String key) {
		super(message, key);
	}
}
