package com.dio.pontodeacesso.core.swagger;


import java.util.Arrays;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                    .title("Aplicação Ponto de Acesso")
                    .description("Esta é uma aplicação Rest que simula um ponto de acesso para funcionários de uma empresa")
                    .termsOfService("terms").contact(new Contact().email("ornelaz95@gmail.com"))
                    .license(new License().name("GNU"))
                    .version("1.0.0"));
    }
}

