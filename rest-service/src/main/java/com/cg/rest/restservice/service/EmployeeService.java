package com.cg.rest.restservice.service;

import java.util.List;
import java.util.Optional;

import com.cg.rest.restservice.pojo.Employee;

public interface EmployeeService {

	public void addNewEmployee(Employee employee);
	
	List<Employee> getAllEmployees();

	public void updateEmployee(Employee employee);

	public void deleteEmployee(int empId);

	public Optional<Employee> getEmployee(int empId);
}
