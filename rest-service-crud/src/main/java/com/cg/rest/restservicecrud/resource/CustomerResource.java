package com.cg.rest.restservicecrud.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.rest.restservicecrud.entity.Customer;
import com.cg.rest.restservicecrud.service.CustomerService;


@RestController
@RequestMapping("/customers")
public class CustomerResource {
	@Autowired
	private CustomerService service;
	
	@PostMapping//UNIFORM Constraint Interface
	public void addNewCustomer(@RequestBody Customer customer) {
		service.addNewCustomer(customer);
	}
	@GetMapping//UNIFORM Constraint Interface
	public List<Customer> getAllCustomers(){
		return service.getAllCustomers();
	}
	@PutMapping
	public void updateCustomer(@RequestBody Customer customer) {
		service.updateCustomer(customer);
	}
	@DeleteMapping("/{customerId}")
	public void deleteCustomer(@PathVariable int customerId) {
		service.deleteCustomer(customerId);
	}
	@GetMapping("/{customerId}")
	public Optional<Customer> getById(@PathVariable int customerId) {
		return  service.getById(customerId);
	}
}
