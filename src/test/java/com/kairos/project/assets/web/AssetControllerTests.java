package com.kairos.project.assets.web;

import com.kairos.project.assets.model.Asset;
import com.kairos.project.assets.model.AssetRepository;
import com.kairos.project.base.ProjectBaseTest;
import com.kairos.project.departments.model.DepartmentRepository;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.kairos.core.web.ApiConstants.Assets.ASSETS_API;
import static com.kairos.core.web.ApiConstants.Assets.ASSET_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;

public class AssetControllerTests extends ProjectBaseTest {

	@Autowired
	AssetRepository assetRepository;
	@Autowired
	DepartmentRepository departmentRepository;

	@BeforeEach
	void setUp() {
		assetRepository.deleteAll();
		departmentRepository.deleteAll();
	}

	@Test
	void shouldGetAll() {
		var entities = List.of(
				new Asset(null, "Asset 1", "KKS 1"),
				new Asset(null, "Asset 2", "KKS 2"));
		assetRepository.saveAll(entities);

		givenEditorAuth().contentType(ContentType.JSON).when()
				.get(ASSETS_API).then()
				.statusCode(200)
				.body("_embedded.data", hasSize(2))
				.body("_embedded.data[0].id", notNullValue())
				.body("_embedded.data[0].name", is("Asset 1"))
				.body("_embedded.data[0].assetNumber", is("KKS 1"))
				.body("_embedded.data[1].id", notNullValue())
				.body("_embedded.data[1].name", is("Asset 2"))
				.body("_embedded.data[1].assetNumber", is("KKS 2"));
	}

	@Test
	void shouldGetById() {
		var asset = new Asset(null, "Asset 1", "KKS 1");
		var entity = assetRepository.save(asset);

		givenEditorAuth().contentType(ContentType.JSON).when()
				.get(ASSETS_API + ASSET_ID, entity.getId())
				.then()
				.statusCode(200)
				.body("name", is(asset.getName()))
				.body("assetNumber", is(asset.getAssetNumber()));
	}

	@Test
	@SneakyThrows
	void shouldCreateSuccessfully() {
		var request = new AssetRequest("Asset 1", "kks 1");
		givenEditorAuth().contentType(ContentType.JSON)
				.body(jsonMapper.writeValueAsString(request)).when()
				.post(ASSETS_API)
				.then()
				.statusCode(200)
				.body("name", is(request.getName()))
				.body("assetNumber", is(request.getAssetNumber()));
	}

	@Test
	void shouldDeleteById() {
		var entity = assetRepository.save(new Asset(null, "Asset 1", "KKS 1"));

		assertThat(assetRepository.findById(entity.getId())).isPresent();

		givenWithReadOnlyAuth().when()
				.delete(ASSETS_API + ASSET_ID, entity.getId())
				.then().statusCode(HttpStatus.FORBIDDEN.value());

		givenEditorAuth().contentType(ContentType.JSON).when()
				.delete(ASSETS_API + ASSET_ID, entity.getId())
				.then()
				.statusCode(200);

		assertThat(assetRepository.findById(entity.getId())).isEmpty();
	}
}
