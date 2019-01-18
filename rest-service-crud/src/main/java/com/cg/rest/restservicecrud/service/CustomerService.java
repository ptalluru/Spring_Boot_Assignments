package com.cg.rest.restservicecrud.service;

import java.util.List;
import java.util.Optional;

import com.cg.rest.restservicecrud.entity.Customer;

public interface CustomerService {


	void addNewCustomer(Customer customer);

	List<Customer> getAllCustomers();

	void updateCustomer(Customer customer);

	void deleteCustomer(int customerId);

	Optional<Customer> getById(int customerId);

}