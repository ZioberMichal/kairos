package com.kairos.core.exceptions;

import com.kairos.core.web.model.FieldError;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class EntityNotFoundException extends DomainException {

	public static final String NOT_FOUND_KEY = "not.found";

	public EntityNotFoundException(Serializable id) {
		super("Entity with id '" + id + "' not found",
				NOT_FOUND_KEY,
				List.of(FieldError.builder().field("id").messageKey(NOT_FOUND_KEY).value(id).build()));
	}

	public EntityNotFoundException(Serializable id, Serializable... relations) {
		super("Entity with id '" + id + "' not found in relation with " + Arrays.toString(relations),
				NOT_FOUND_KEY,
				List.of(FieldError.builder().field("id").messageKey(NOT_FOUND_KEY).value(id).build()));
	}
}
