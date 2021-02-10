package com.makeen.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MultiModuleSpringApp extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run( MultiModuleSpringApp.class, args );
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources( MultiModuleSpringApp.class );
    }
}
