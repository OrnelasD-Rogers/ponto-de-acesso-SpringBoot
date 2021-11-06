package com.dio.pontodeacesso;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition
@SpringBootApplication
public class PontoDeAcessoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontoDeAcessoApplication.class, args);
	}

}
