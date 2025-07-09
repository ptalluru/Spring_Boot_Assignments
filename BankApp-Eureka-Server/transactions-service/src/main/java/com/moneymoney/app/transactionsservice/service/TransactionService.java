package com.moneymoney.app.transactionsservice.service;

import java.time.LocalDate;
import java.util.List;

import com.moneymoney.app.transactionsservice.entity.Transaction;

public interface TransactionService {
	
	List<Transaction> getStatement(LocalDate startDate, LocalDate endDate);

    Double withdraw(int accountNumber, String transactionDetails, double currentBalance, double amount);

    Double deposit(int accountNumber, String transactionDetails, double currentBalance, double amount);

	List<Transaction> getStatement();}