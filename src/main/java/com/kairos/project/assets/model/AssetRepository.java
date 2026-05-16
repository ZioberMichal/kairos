package com.kairos.project.assets.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
	boolean existsByNameAndIdNot(String name, Long id);
	boolean existsByAssetNumberAndIdNot(String assetNumber, Long id);
}
