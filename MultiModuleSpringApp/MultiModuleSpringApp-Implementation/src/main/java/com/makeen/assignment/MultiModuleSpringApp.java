package com.makeen.assignment;

import com.makeen.assignment.repository.UserRepository;
import com.makeen.assignment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MultiModuleSpringApp extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;


	public static void main(final String[] args) {
		SpringApplication.run( MultiModuleSpringApp.class, args);
	}

	@Override
	public void run(String...args) throws Exception {
		userRepository.save(new User("kamesh.kr258@gmail.com", bCryptPasswordEncoder.encode("kamesh@123")));
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MultiModuleSpringApp.class);
	}
}
