package com.kairos.project.employees.web;

import lombok.Data;

@Data
public class EmployeeResponse {

	private long id;
	private String firstname;
	private String lastname;
	private String assignedId;
}
