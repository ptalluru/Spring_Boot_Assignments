package com.cg.rest.restservice.service;

import java.util.List;

import com.cg.rest.restservice.pojo.Employee;

public interface EmployeeService {

	public void addNewEmployee(Employee employee);
	
	List<Employee> getAllEmployees();
}
