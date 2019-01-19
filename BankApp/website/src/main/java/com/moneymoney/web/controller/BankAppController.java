package com.moneymoney.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.moneymoney.web.entity.CurrentDataSet;
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
		restTemplate.postForEntity("http://localhost:8989/transactions", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
	
	@RequestMapping("/statement")
	public ModelAndView getStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet=restTemplate.getForObject("http://localhost:8989/transactions?offset="+offset+"&size="+size, 
				CurrentDataSet.class );
		//modelView.addObject("dataSet",currentDataSet);
		int currentSize = size == 0 ? 5 : size;
		int currentOffset = offset == 0 ? 1 : offset;
		Link next = linkTo(methodOn(BankAppController.class).getStatement(currentOffset+currentSize, currentSize)).withRel("next");
		Link previous = linkTo(methodOn(BankAppController.class).getStatement(currentOffset-currentSize, currentSize)).withRel("previous");
		currentDataSet.setNextLink(next);
		currentDataSet.setPreviousLink(previous);
		ModelAndView model = new ModelAndView();
		model.addObject("dataSet",currentDataSet);
		model.setViewName("DepositForm");
		return model;
	}
}
