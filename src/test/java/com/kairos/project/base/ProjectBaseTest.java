package com.kairos.project.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public abstract class ProjectBaseTest {

	public static ProjectMySQLContainer sqlContainer = ProjectMySQLContainer.getInstance();

	@LocalServerPort
	protected Integer port;

	@Autowired
	protected ObjectMapper jsonMapper;

	@BeforeEach
	void baseSetUp() {
		RestAssured.baseURI = "http://localhost:" + port;
	}

	protected RequestSpecification givenEditorAuth() {
		return given().auth().basic("editor", "Editor1");
	}

	protected RequestSpecification givenWithReadOnlyAuth() {
		return given().auth().basic("reader", "Reader1");
	}
}
