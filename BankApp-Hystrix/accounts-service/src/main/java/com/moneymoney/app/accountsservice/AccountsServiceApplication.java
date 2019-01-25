package com.moneymoney.app.accountsservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.moneymoney.app.accountsservice.entity.CurrentAccount;
import com.moneymoney.app.accountsservice.entity.SavingsAccount;
import com.moneymoney.app.accountsservice.repository.AccountsRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateData(AccountsRepository accountsRepository) {
		return (arg) -> {
			accountsRepository.save(new SavingsAccount(101,"Ajay",10_000.00,true));
			accountsRepository.save(new SavingsAccount(102,"Prasanth",0.0,true));
			accountsRepository.save(new SavingsAccount(103,"Navya",25_000.00,false));
			accountsRepository.save(new CurrentAccount(104,"yugesh",1_00_000.00,5_000.00));
			accountsRepository.save(new CurrentAccount(105,"Prasanth Kumar Talluru",1_00_000.00,5_000.00));
			accountsRepository.save(new CurrentAccount(106,"Talluru Prasanth Kumar",1_00_000.00,5_000.00));
		};
	}
	
}
