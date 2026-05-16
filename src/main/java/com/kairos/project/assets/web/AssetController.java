package com.kairos.project.assets.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kairos.project.assets.model.AssetService;

import static com.kairos.core.web.ApiConstants.Assets.ASSETS_API;
import static com.kairos.core.web.ApiConstants.Assets.ASSET_ID;
import static com.kairos.core.web.ApiConstants.Assets.Perms.ASSETS_DELETE;
import static com.kairos.core.web.ApiConstants.Assets.Perms.ASSETS_READ;
import static com.kairos.core.web.ApiConstants.Assets.Perms.ASSETS_UPDATE;

@RestController
@RequestMapping(value = ASSETS_API, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AssetController {

	private final AssetService assetService;
	private final AssetMapper assetMapper;
	private final AssetResponseProcessor assetResponseProcessor;

	@GetMapping
	@PreAuthorize("hasAuthority('" + ASSETS_READ + "')")
	public CollectionModel<EntityModel<AssetResponse>> getAll() {
		var assets = assetService.listAll();
		return assetResponseProcessor.buildList(assets);
	}

	@GetMapping(ASSET_ID)
	@PreAuthorize("hasAuthority('" + ASSETS_READ + "')")
	public EntityModel<AssetResponse> getById(@PathVariable Long assetId) {
		var asset = assetService.getById(assetId);
		return assetResponseProcessor.buildOne(asset);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('" + ASSETS_UPDATE + "')")
	public EntityModel<AssetResponse> createNew(@Valid @RequestBody AssetRequest request) {
		var asset = assetMapper.fromRequest(request);
		var savedAsset = assetService.save(asset);
		return assetResponseProcessor.buildOne(savedAsset);
	}

	@PostMapping(ASSET_ID)
	@PreAuthorize("hasAuthority('" + ASSETS_UPDATE + "')")
	public EntityModel<AssetResponse> update(@PathVariable Long assetId, @Valid @RequestBody AssetRequest request) {
		var existing = assetService.getById(assetId);
		assetMapper.updateRequest(request, existing);
		var updated = assetService.save(existing);
		return assetResponseProcessor.buildOne(updated);
	}

	@PatchMapping(ASSET_ID)
	@PreAuthorize("hasAuthority('" + ASSETS_UPDATE + "')")
	public EntityModel<AssetResponse> patch(@PathVariable Long assetId, @Valid @RequestBody AssetPatchRequest request) {
		var existing = assetService.getById(assetId);
		assetMapper.patchRequest(request, existing);
		var updated = assetService.save(existing);
		return assetResponseProcessor.buildOne(updated);
	}

	@DeleteMapping(ASSET_ID)
	@PreAuthorize("hasAuthority('" + ASSETS_DELETE + "')")
	public ResponseEntity<Void> delete(@PathVariable Long assetId) {
		assetService.delete(assetId);

		return ResponseEntity.ok().build();
	}
}
