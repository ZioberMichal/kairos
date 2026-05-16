package com.kairos.core.exceptions;

import lombok.experimental.UtilityClass;
import com.kairos.core.web.model.FieldError;
import com.kairos.core.web.model.FieldError.FieldErrorBuilder;

import java.io.Serializable;
import java.util.List;

import static java.util.Objects.requireNonNull;

@UtilityClass
public class Exceptions {

	public static EntityNotFoundException notFound(Serializable id) {
		return new EntityNotFoundException(requireNonNull(id));
	}

	public static EntityNotFoundException notFound(Serializable id, Serializable... relations) {
		return new EntityNotFoundException(requireNonNull(id), requireNonNull(relations));
	}

	public static BadDataException badData(String message, List<FieldError> errors) {
		return new BadDataException(requireNonNull(message), errors);
	}

	public static BadDataException badData(String message, FieldError error) {
		return badData(message, List.of(error));
	}

	public static BadDataException badData(String message, FieldErrorBuilder builder) {
		return badData(message, builder.build());
	}

	public static BadRequestException badRequest(String message, String key) {
		return new BadRequestException(message, key);
	}
}
