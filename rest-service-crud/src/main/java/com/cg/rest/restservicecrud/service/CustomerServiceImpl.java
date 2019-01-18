package com.cg.rest.restservicecrud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.rest.restservicecrud.entity.Customer;
import com.cg.rest.restservicecrud.repo.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public void addNewCustomer(Customer customer) {
		repository.save(customer);
	}

	@Override
	public List<Customer> getAllCustomers() {
		
		return repository.findAll();
	}

	@Override
	public void updateCustomer(Customer customer) {
		repository.save(customer);
	}

	@Override
	public void deleteCustomer(int customerId) {
		repository.deleteById(customerId);
	}

	@Override
	public Optional<Customer> getById(int customerId) {
	return repository.findById(customerId);
	}

}
