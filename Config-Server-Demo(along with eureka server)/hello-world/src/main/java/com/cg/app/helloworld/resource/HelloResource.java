package com.cg.app.helloworld.resource;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/helloresources")
public class HelloResource {
	
	@GetMapping
	public ResponseEntity<String> hello(){
		return new ResponseEntity<String>("HELLO WORLD",HttpStatus.OK);
	}

}
