package com.cg.rest.restservice.pojo;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private int flatno;
	private String street;
	private String city;
	private int pincode;

	public Address() {
	}
	
	public Address(int flatno, String street, String city, int pincode) {
		super();
		this.flatno = flatno;
		this.street = street;
		this.city = city;
		this.pincode = pincode;
	}

	public int getFlatno() {
		return flatno;
	}
	public void setFlatno(int flatno) {
		this.flatno = flatno;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPincode() {
		return pincode;
	}
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	@Override
	public String toString() {
		return "Address [flatno=" + flatno + ", street=" + street + ", city=" + city + ", pincode=" + pincode + "]";
	}
	
	
}
