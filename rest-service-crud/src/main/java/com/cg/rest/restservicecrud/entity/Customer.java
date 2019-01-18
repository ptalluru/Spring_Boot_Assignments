package com.cg.rest.restservicecrud.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer {
	@Id
	private int customerId;
	private String customerName;
	private Address address;

	public Customer(int customerId, String customerName, Address address) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.address = address;
	}

	public Customer() {
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", address=" + address + "]";
	}
}
