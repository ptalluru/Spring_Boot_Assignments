package com.moneymoney.app.transactionsservice;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.moneymoney.app.transactionsservice.entity.Transaction;
import com.moneymoney.app.transactionsservice.entity.TransactionType;
import com.moneymoney.app.transactionsservice.repository.TransactionRepository;
import com.moneymoney.app.transactionsservice.service.TransactionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionServiceImpl service;

    @Test
    public void depositShouldIncreaseBalance() {
        double newBalance = service.deposit(1, "deposit", 100.0, 50.0);
        assertEquals(150.0, newBalance, 0);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).save(captor.capture());
        Transaction saved = captor.getValue();
        assertEquals(Integer.valueOf(1), saved.getAccountNumber());
        assertEquals(Double.valueOf(50.0), saved.getAmount());
        assertEquals(Double.valueOf(150.0), saved.getCurrentBalance());
        assertEquals(TransactionType.DEPOSIT, saved.getTransactionType());
    }

    @Test
    public void withdrawShouldDecreaseBalance() {
        double newBalance = service.withdraw(1, "withdraw", 200.0, 75.0);
        assertEquals(125.0, newBalance, 0);
        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(repository).save(captor.capture());
        Transaction saved = captor.getValue();
        assertEquals(Integer.valueOf(1), saved.getAccountNumber());
        assertEquals(Double.valueOf(75.0), saved.getAmount());
        assertEquals(Double.valueOf(125.0), saved.getCurrentBalance());
        assertEquals(TransactionType.WITHDRAW, saved.getTransactionType());
    }
}
