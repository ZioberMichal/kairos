package com.kairos.project.assets.web;

import com.kairos.project.assets.model.Asset;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@Component
@AllArgsConstructor
public class AssetResponseProcessor {

	private final AssetMapper assetMapper;

	EntityModel<AssetResponse> buildOne(Asset entity) {
		var response = assetMapper.toResponse(entity);

		return addLinks(response);
	}

	CollectionModel<EntityModel<AssetResponse>> buildList(Collection<Asset> entities) {
		var responses = entities.stream().map(assetMapper::toResponse).map(this::addLinks).toList();
		var models = CollectionModel.of(responses);
		models.add(linkTo(AssetController.class).withRel("create").withType(POST.name()));

		return models;
	}

	private EntityModel<AssetResponse> addLinks(AssetResponse response) {
		var model = EntityModel.of(response);
		model.add(linkTo(AssetController.class).slash(response.getId()).withSelfRel());
		model.add(linkTo(AssetController.class).slash(response.getId()).withRel("update").withType(POST.name()));
		model.add(linkTo(AssetController.class).slash(response.getId()).withRel("patch").withType(PATCH.name()));
		model.add(linkTo(AssetController.class).slash(response.getId()).withRel("delete").withType(DELETE.name()));

		return model;
	}
}
