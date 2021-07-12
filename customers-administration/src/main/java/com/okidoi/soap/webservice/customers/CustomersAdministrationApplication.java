package com.okidoi.soap.webservice.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomersAdministrationApplication {

	//OBS: Podemos verificar o wsl em http://localhost:8080/ws/customers.wsdl
	
	public static void main(String[] args) {
		SpringApplication.run(CustomersAdministrationApplication.class, args);
	}

}
