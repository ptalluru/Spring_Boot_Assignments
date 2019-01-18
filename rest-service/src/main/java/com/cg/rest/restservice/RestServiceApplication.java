package com.cg.rest.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cg.rest.restservice.pojo.Address;
import com.cg.rest.restservice.pojo.Employee;
import com.cg.rest.restservice.repo.EmployeeRepository;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner populateData(EmployeeRepository repository) {
		return (arg)->{
			repository.save(new Employee(106,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
			repository.save(new Employee(107,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
			repository.save(new Employee(108,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
			repository.save(new Employee(109,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
			repository.save(new Employee(110,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
			repository.save(new Employee(111,"Prasanth Kumar", new Address(100,"Airoli","Mumbai", 524407), 10000));
		};
	}
}

