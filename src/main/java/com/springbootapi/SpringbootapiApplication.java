package com.springbootapi;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Spring Boot Blog APP Rest API's",
		description = "Spring Boot Rest API Documentation",
		version = "v1.0",
		contact = @Contact(
				name = "Rohit",
				email = "bonirohit@gmail.com",
				url = "github.com/rohit-code"
		)),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/Rohit-code/springbootapi"
		)
)
public class SpringbootapiApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringbootapiApplication.class, args);
	}

}
