package com.moneymoney.web.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Controller
@EnableCircuitBreaker
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/")
	public String form() {
		return "index";
	}
	
	@RequestMapping("/depositForm")
	public String depositForm() {
		return "DepositForm";
	}
	
	@HystrixCommand(fallbackMethod="fallbackDeposit")
	@RequestMapping("/deposit")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://transactions-service/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "DepositForm";
	}

	public String fallbackDeposit(@ModelAttribute Transaction transaction,
			Model model) {
		model.addAttribute("message","Deposit service unavailable.....try after sometime");
		return "DepositForm";
	}
	
	
	
	@RequestMapping("/withdrawForm")
	public String withdrawForm() {
		return "WithdrawForm";
	}
	
	@HystrixCommand(fallbackMethod="fallbackWithdraw")
	@RequestMapping("/withdraw")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://transactions-service/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "WithdrawForm";
	}
	
	
	public String fallbackWithdraw(@ModelAttribute Transaction transaction,
			Model model) {
		model.addAttribute("message","Withdraw service unavailable.....try after sometime!");
		return "WithdrawForm";
	}
	
	@RequestMapping("/fundTransferForm")
	public String fundTransferForm() {
		return "FundTransferForm";
	}
	
	@HystrixCommand(fallbackMethod="fallbackFundTransfer")
	@RequestMapping("/fundTransfer")
	public String fundTransfer(@RequestParam("senderAccountNumber") int senderAccountNumber,
			@RequestParam("receiverAccountNumber") int receiverAccountNumber,
			@RequestParam("amount") double amount,@ModelAttribute Transaction transaction, Model model) {
		transaction.setAccountNumber(senderAccountNumber);
		restTemplate.postForEntity("http://transactions-service/transactions/withdraw", transaction, null);
		transaction.setAccountNumber(receiverAccountNumber);
		restTemplate.postForEntity("http://transactions-service/transactions/deposit", transaction, null);
		model.addAttribute("message","Success!");
		return "FundTransferForm";
	}
	
	public String fallbackFundTransfer(@RequestParam("senderAccountNumber") int senderAccountNumber,
			@RequestParam("receiverAccountNumber") int receiverAccountNumber,
			@RequestParam("amount") double amount,@ModelAttribute Transaction transaction, Model model) {
		
		model.addAttribute("message","FundTransfer service unavailable.....try after sometime!");
		return "FundTransferForm";
	}
	
	
	/*
	 * @RequestMapping("/statement") public ModelAndView
	 * getStatement(@RequestParam("offset") int offset, @RequestParam("size") int
	 * size) { CurrentDataSet currentDataSet=restTemplate.getForObject(
	 * "http://localhost:8989/transactions?offset="+offset+"&size="+size,
	 * CurrentDataSet.class ); currentDataSet.getTransactions();
	 * //modelView.addObject("dataSet",currentDataSet); int currentSize = size == 0
	 * ? 5 : size; int currentOffset = offset == 0 ? 1 : offset; Link next =
	 * linkTo(methodOn(BankAppController.class).getStatement(currentOffset+
	 * currentSize, currentSize)).withRel("next"); Link previous =
	 * linkTo(methodOn(BankAppController.class).getStatement(currentOffset-
	 * currentSize, currentSize)).withRel("previous");
	 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
	 * ModelAndView model = new ModelAndView();
	 * model.addObject("dataSet",currentDataSet); model.setViewName("DepositForm");
	 * return model; }
	 */
	
	@HystrixCommand(fallbackMethod="fallbackDepositStatement")
	@RequestMapping("/statementDeposit")
	public ModelAndView getStatementDeposit(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://transactions-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("DepositForm");
		return modelView;
	}
	
	
	
	public ModelAndView fallbackDepositStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset-currentSize, currentSize)).withRel("previous");
		
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if(currentOffset<1) 
				  break;
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("DepositForm");
		modelView.addObject("message","Statements not available ...try again later!!");
		return modelView;
	}
	
	
	
	@HystrixCommand(fallbackMethod="fallbackWithdrawStatement")
	@RequestMapping("/statementWithdraw")
	public ModelAndView getStatementWithdraw(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://transactions-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementWithdraw(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementWithdraw(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("WithdrawForm");
		return modelView;
	}
	
	public ModelAndView fallbackWithdrawStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementWithdraw(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementWithdraw(currentOffset-currentSize, currentSize)).withRel("previous");
		
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if(currentOffset<1) 
				  break;
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("WithdrawForm");
		modelView.addObject("message","Statements not available ...try again later!!");
		return modelView;
	}
	
	@HystrixCommand(fallbackMethod="fallbackFundTransferStatement")
	@RequestMapping("/statementFundTransfer")
	public ModelAndView getStatementFundTransfer(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://transactions-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementFundTransfer(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementFundTransfer(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("FundTransferForm");
		return modelView;
	}
	
	public ModelAndView fallbackFundTransferStatement(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementFundTransfer(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementFundTransfer(currentOffset-currentSize, currentSize)).withRel("previous");
		
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if(currentOffset<1) 
				  break;
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		/*
		 * currentDataSet.setNextLink(next); currentDataSet.setPreviousLink(previous);
		 */
		ModelAndView modelView = new ModelAndView();
		modelView.addObject("currentDataSet",dataSet);
		modelView.setViewName("FundTransferForm");
		modelView.addObject("message","Statements not available ...try again later!!");
		return modelView;
	}}