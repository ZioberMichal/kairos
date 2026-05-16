package com.kairos.core.security;

import lombok.experimental.UtilityClass;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class SecurityUtils {

	public static Map<RequestMethod, String> findPermsMapping(Class<?> permClass) {
		var mapping = new HashMap<RequestMethod, String>(6);
		ReflectionUtils.doWithLocalFields(permClass, field -> {
			if (Modifier.isStatic(field.getModifiers())) {
				var allowedMethods = field.getAnnotation(AllowedMethods.class);
				if (Objects.nonNull(allowedMethods)) {
					var permission = Objects.toString(field.get(null), null);
					if (StringUtils.hasLength(permission)) {
						Arrays.stream(allowedMethods.value())
								.iterator().forEachRemaining(method -> mapping.put(method, permission));
					}
				}
			}
		});

		return mapping;
	}
}
