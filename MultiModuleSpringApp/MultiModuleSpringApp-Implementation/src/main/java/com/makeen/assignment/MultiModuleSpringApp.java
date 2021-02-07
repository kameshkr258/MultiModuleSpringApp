package com.makeen.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MultiModuleSpringApp {

	public static void main(final String[] args) {
		SpringApplication.run( MultiModuleSpringApp.class, args);
	}
	@RequestMapping(value = "/")
	public String hello() {
		return "finally Name Changed";
	}

}
