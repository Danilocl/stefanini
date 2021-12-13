package com.br.booksManagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Books API", version = "1.0", description = "Book management API"))
public class BooksManagementApplication {

		
	public static void main(String[] args) {
		SpringApplication.run(BooksManagementApplication.class, args);
	}

}
