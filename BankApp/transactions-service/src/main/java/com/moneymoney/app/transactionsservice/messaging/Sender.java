package com.moneymoney.app.transactionsservice.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.moneymoney.app.transactionsservice.entity.Transaction;

@Component
public class Sender {

	@Autowired
	RabbitMessagingTemplate template;
	
	@Bean
	public Queue queue() {
		return new Queue("messaging",false);
	}
	
	public void updateBalance(Transaction transaction) {
		template.convertAndSend("messaging",transaction);
	}
}
