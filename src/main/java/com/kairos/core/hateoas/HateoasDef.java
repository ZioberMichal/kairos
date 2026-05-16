package com.kairos.core.hateoas;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HateoasDef {
	private final Class<?> clazz;
	private final String collection;
}
