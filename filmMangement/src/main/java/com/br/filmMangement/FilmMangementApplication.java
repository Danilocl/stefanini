package com.br.filmMangement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Films API", version = "1.0", description = "Films management API"))
public class FilmMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmMangementApplication.class, args);
	}

}
