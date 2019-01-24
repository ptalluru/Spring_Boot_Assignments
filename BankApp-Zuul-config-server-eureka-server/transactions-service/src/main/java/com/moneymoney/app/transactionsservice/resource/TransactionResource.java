package com.moneymoney.app.transactionsservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.app.transactionsservice.entity.Transaction;
import com.moneymoney.app.transactionsservice.service.TransactionService;

@RefreshScope
@RestController
@RequestMapping("/transactions")
public class TransactionResource {

	@Autowired
	private TransactionService service;
	@Autowired
	private RestTemplate restTemplate;

	@PostMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.deposit(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.withdraw(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		restTemplate.put(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "?currentBalance=" + updateBalance,
				null);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/*
	 * @GetMapping public CurrentDataSet getstatement(@RequestParam("offset") int
	 * offset, @RequestParam("size") int size) {
	 * 
	 * int currentSize = size == 0 ? 5 : size; int currentOffset = offset == 0 ? 1 :
	 * offset; Link next =
	 * linkTo(methodOn(TransactionResource.class).getstatement(6,
	 * 5)).withRel("next"); Link previous =
	 * linkTo(methodOn(TransactionResource.class).getstatement(0,
	 * 5)).withRel("previous");
	 * 
	 * List<Transaction> transactions = service.getStatement(); List<Transaction>
	 * currentDataSet = new ArrayList<Transaction>(); for (int i = currentOffset -
	 * 1; i < currentSize + currentOffset - 1; i++) { Transaction transaction =
	 * transactions.get(i); currentDataSet.add(transaction); } CurrentDataSet
	 * dataSet = new CurrentDataSet(currentDataSet, next, previous); return dataSet;
	 * }
	 */
	@GetMapping("/statement")
	public ResponseEntity<CurrentDataSet> getStatement(){
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = service.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet,HttpStatus.OK);
	}
}
