package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import com.example.demo.control.BookController;

@SpringBootApplication
@EnableAsync
@ComponentScan
public class BookWeb2Application extends SpringBootServletInitializer{
	@Autowired
	public BookController service;

	public static void main(String[] args) {
		SpringApplication.run(BookWeb2Application.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BookWeb2Application.class);
	}
	

}
