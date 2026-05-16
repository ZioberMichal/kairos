package com.kairos.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.function.Supplier;

@UtilityClass
public class Validate {

	public static Condition ifNull(Object value) {
		return when(Objects.nonNull(value));
	}

	public static Condition ifFalse(boolean condition) {
		return ifTrue(!condition);
	}

	public static Condition ifTrue(boolean value) {
		return when(value);
	}

	public static Condition when(boolean condition) {
		return new Condition(condition);
	}

	@AllArgsConstructor
	public static class Condition {
		private final boolean condition;

		public void fail(Supplier<? extends RuntimeException> supplier) {
			if (condition) {
				throw supplier.get();
			}
		}
	}
}
