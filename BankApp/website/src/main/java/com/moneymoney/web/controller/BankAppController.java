package com.moneymoney.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.web.entity.Transaction;

@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String depositForm() {
		return "DepositForm";
	}
	@RequestMapping("/deposit")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		//System.out.println("transaction "+transaction);
		restTemplate.postForEntity("http://localhost:8989/transactions", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
}
