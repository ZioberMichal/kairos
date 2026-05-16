package com.kairos.project.assets.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.kairos.core.exceptions.Exceptions.badData;
import static com.kairos.core.exceptions.Exceptions.notFound;
import static com.kairos.core.exceptions.Validate.ifTrue;
import static com.kairos.core.web.model.FieldError.alreadyExists;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetService {
	private final AssetRepository assetRepository;

	public List<Asset> listAll() {
		return assetRepository.findAll(Sort.by(Order.by("name"), Order.asc("assetNumber")));
	}

	public Asset getById(Long id) {
		return assetRepository.findById(id).orElseThrow(() -> notFound(id));
	}

	public Asset save(Asset asset) {
		validate(asset);

		return assetRepository.save(asset);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(Long id) {
		var asset = getById(id);
		assetRepository.deleteById(id);
		log.info("Delete {} with {}", asset.getName(), asset.getAssetNumber());
	}

	void validate(Asset asset) {
		final var assetIdOrNonExistent = Objects.requireNonNullElse(asset.getId(), 0L);
		ifTrue(assetRepository.existsByNameAndIdNot(asset.getName(), assetIdOrNonExistent))
				.fail(() -> badData("Name already exists!", alreadyExists("name", asset.getName())));
		ifTrue(assetRepository.existsByAssetNumberAndIdNot(asset.getAssetNumber(), assetIdOrNonExistent))
				.fail(() -> badData("Asset number code already exists!", alreadyExists("assetNumber", asset.getAssetNumber())));
	}
}
