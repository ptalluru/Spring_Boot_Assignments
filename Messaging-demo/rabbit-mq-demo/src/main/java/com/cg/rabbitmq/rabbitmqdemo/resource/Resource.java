package com.cg.rabbitmq.rabbitmqdemo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.rabbitmq.rabbitmqdemo.messaging.Sender;

@RestController
public class Resource {

	@Autowired
	Sender sender;
	
	@RequestMapping
	public void hello() {
		sender.send("HELLO WORLD");
	}
}