package com.kairos.core.hateoas;

import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class HateoasLinkRelationProvider implements LinkRelationProvider {

	private final Map<Class<?>, String> collectionDefinitions;

	public HateoasLinkRelationProvider() {
		var definitions = definitions();
		collectionDefinitions = definitions.stream()
				.collect(Collectors.toMap(HateoasDef::getClazz, HateoasDef::getCollection));
	}

	protected abstract List<HateoasDef> definitions();

	@NonNull
	@Override
	public LinkRelation getItemResourceRelFor(@NonNull Class<?> type) {
		return LinkRelation.of("");
	}

	@NonNull
	@Override
	public LinkRelation getCollectionResourceRelFor(@NonNull Class<?> type) {
		return LinkRelation.of(collectionDefinitions.get(type));
	}

	@Override
	public boolean supports(LookupContext delimiter) {
		return collectionDefinitions.containsKey(delimiter.getType());
	}
}
