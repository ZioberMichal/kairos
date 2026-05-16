package com.kairos.project.employees.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	public List<Employee> listAll() {
		return employeeRepository.findAll();
	}
}
