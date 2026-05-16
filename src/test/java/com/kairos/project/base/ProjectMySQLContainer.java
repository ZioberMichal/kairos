package com.kairos.project.base;

import org.testcontainers.containers.MySQLContainer;

public class ProjectMySQLContainer extends MySQLContainer<ProjectMySQLContainer> {

	private static final String IMAGE_VERSION = "mysql:8";
	private static ProjectMySQLContainer container;

	private ProjectMySQLContainer() {
		super(IMAGE_VERSION);
	}

	public static ProjectMySQLContainer getInstance() {
		if (container == null) {
			container = new ProjectMySQLContainer();
			container.start();
		}
		return container;
	}

	@Override
	public void start() {
		super.start();
		System.setProperty("DB_URL", container.getJdbcUrl());
		System.setProperty("DB_USERNAME", container.getUsername());
		System.setProperty("DB_PASSWORD", container.getPassword());
	}

	@Override
	public void stop() {
		//do nothing, JVM handles shut down
	}
}
