package com.kairos.config;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.kairos.core.exceptions.BadDataException;
import com.kairos.core.exceptions.BadRequestException;
import com.kairos.core.exceptions.DomainException;
import com.kairos.core.exceptions.EntityNotFoundException;
import com.kairos.core.web.model.ApiError;
import com.kairos.core.web.model.FieldError;

import java.util.List;

import static com.kairos.core.web.model.FieldError.ofName;

@Slf4j
@ControllerAdvice
public class ExceptionsControllerAdvice {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(DomainException exc) {
		log.warn(exc.getMessage(), exc);

		return create(fromException(HttpStatus.NOT_FOUND, exc));
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<ApiError> handleDaoNotFound(EmptyResultDataAccessException exc) {
		log.warn(exc.getMessage(), exc);

		return create(fromException(HttpStatus.NOT_FOUND, exc));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException exc) {
		log.warn(exc.getMessage(), exc);

		return create(fromException(HttpStatus.BAD_REQUEST, exc));
	}

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiError> handleValidation(ValidationException exc) {
		log.warn(exc.getMessage(), exc);

		return create(fromException(HttpStatus.BAD_REQUEST, exc));
	}

	@ExceptionHandler({DomainException.class, BadDataException.class, BadRequestException.class})
	public ResponseEntity<ApiError> handleDomainExc(DomainException exc) {
		log.warn(exc.getMessage());

		return create(fromException(HttpStatus.BAD_REQUEST, exc));
	}

	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<ApiError> handleMethodArgumentNotValidExc(MethodArgumentNotValidException exc) {
		log.warn(exc.getMessage());

		return create(fromException(HttpStatus.BAD_REQUEST, exc, mapFrom(exc.getBindingResult().getAllErrors())));
	}

	private ResponseEntity<ApiError> create(ApiError error) {
		return new ResponseEntity<>(error, error.getStatus());
	}

	ApiError fromException(HttpStatus status, DomainException exception) {
		return ApiError.builder()
				.status(status)
				.statusCode(status.value())
				.messageKey(exception.getMessageKey())
				.errors(exception.getErrors())
				.message(exception.getMessage())
				.build();
	}

	ApiError fromException(HttpStatus status, Exception exception) {
		return fromException(status, exception, List.of());
	}

	ApiError fromException(HttpStatus status, Exception exception, List<FieldError> errors) {
		return ApiError.builder()
				.status(status)
				.statusCode(status.value())
				.messageKey(exception.getClass().getSimpleName())
				.message(exception.getMessage())
				.errors(errors)
				.build();
	}

	private List<FieldError> mapFrom(List<ObjectError> errors) {
		return errors.stream().map(error -> {
			if (error instanceof org.springframework.validation.FieldError e) {
				return ofName(e.getField()).value(e.getRejectedValue()).messageKey(e.getDefaultMessage()).build();
			}
			return ofName("unknown").value(error.getArguments()).messageKey(error.getDefaultMessage()).build();
		}).toList();
	}
}
