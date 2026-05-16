package com.kairos.project.departments.web;

import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;
import com.kairos.project.base.ProjectBaseTest;
import com.kairos.project.departments.model.Department;
import com.kairos.project.departments.model.DepartmentRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static com.kairos.core.web.ApiConstants.Departments.DEPARTMENTS_API;
import static com.kairos.core.web.ApiConstants.PARAM_ID;

public class DepartmentControllerTests extends ProjectBaseTest {

	@Autowired
	DepartmentRepository departmentRepository;

	@Test
	void shouldGetAll() {
		createAndSaveDepartment("Dep " + RandomStringUtils.random(10));

		var departments = departmentRepository.findAll();
		var validateble = givenEditorAuth().contentType(ContentType.JSON).when()
				.get(DEPARTMENTS_API).then()
				.statusCode(200)
				.body("_embedded.data", hasSize(departments.size()));
		for (int i = 0; i < departments.size(); i++) {
			validateble.body("_embedded.data[" + i + "].id", is(departments.get(i).getId().intValue()))
					.body("_embedded.data[" + i + "].name", is(departments.get(i).getName()));
		}
	}

	@Test
	void shouldGetById() {
		var entity = createAndSaveDepartment("Dep " + RandomStringUtils.random(10));

		givenEditorAuth().contentType(ContentType.JSON).when()
				.get(DEPARTMENTS_API + PARAM_ID, entity.getId())
				.then()
				.statusCode(200)
				.body("id", is(entity.getId().intValue()))
				.body("name", is(entity.getName()));
	}

	@Test
	@SneakyThrows
	void shouldCreateSuccessfully() {
		var request = new DepartmentRequest("Dep " + RandomStringUtils.random(10));
		givenEditorAuth().contentType(ContentType.JSON)
				.body(jsonMapper.writeValueAsString(request)).when()
				.post(DEPARTMENTS_API)
				.then()
				.statusCode(200)
				.body("name", is(request.getName()));

		givenWithReadOnlyAuth().contentType(ContentType.JSON)
				.body(jsonMapper.writeValueAsString(request)).when()
				.post(DEPARTMENTS_API)
				.then()
				.statusCode(HttpStatus.FORBIDDEN.value());
	}

	@Test
	@SneakyThrows
	void shouldNotCreateWithSameAttributes() {
		var uniqueName = "UniqueName";
		var entity = createAndSaveDepartment(uniqueName);

		var request = new DepartmentRequest(uniqueName);
		givenEditorAuth().contentType(ContentType.JSON)
				.body(jsonMapper.writeValueAsString(request)).when()
				.post(DEPARTMENTS_API)
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value())
				.body("status", is("400 BAD_REQUEST"))
				.body("statusCode", is(HttpStatus.BAD_REQUEST.value()))
				.body("messageKey", is("bad.data"))
				.body("message", is("Name already exists!"))
				.body("errors[0].field", is("name"))
				.body("errors[0].value", is(entity.getName()))
				.body("errors[0].messageKey", is("already.exists"));
	}

	@Test
	void shouldDeleteById() {
		var entity = createAndSaveDepartment("Dep " + RandomStringUtils.random(10));

		assertThat(departmentRepository.findById(entity.getId())).isPresent();

		givenWithReadOnlyAuth().when()
				.delete(DEPARTMENTS_API + PARAM_ID, entity.getId())
				.then().statusCode(HttpStatus.FORBIDDEN.value());

		givenEditorAuth().contentType(ContentType.JSON).when()
				.delete(DEPARTMENTS_API + PARAM_ID, entity.getId())
				.then()
				.statusCode(200);

		assertThat(departmentRepository.findById(entity.getId())).isEmpty();
	}

	Department createAndSaveDepartment(String name) {
		return departmentRepository.save(new Department(null, name));
	}
}
