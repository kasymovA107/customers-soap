package com.okidoi.soap.webservice.customers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.okidoi.soap.webservice.customers.bean.Customer;
import com.okidoi.soap.webservice.customers.helper.Status;

@Component  //Objeto sendo gerenciado pelo Spring
public class CustomerDetailService {
	

	private static List<Customer> customers = new ArrayList<>();
	
	static {
		
		Customer customer1 = new Customer(1, "John", "111199999999", "john@beatles.com");
		customers.add(customer1);

		Customer customer2 = new Customer(2, "Paul", "222199999999", "paul@beatles.com");
		customers.add(customer2);
		
		Customer customer3 = new Customer(3, "George", "333199999999", "george@beatles.com");
		customers.add(customer3);
		
		Customer customer4 = new Customer(4, "Ringo", "444199999999", "ringo@beatles.com");
		customers.add(customer4);
		
		Customer customer5 = new Customer(5, "Yoko", "66699999999", "yoko@beatles.com");
		customers.add(customer5);
		
		
	}
	
	
	public Customer findById(int id) {
		
		Optional<Customer> customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		
		if(customerOptional.isPresent()) {
			return customerOptional.get();
		}
		return null;
	}

	public List<Customer> findAll(){
		return customers;
	}

	public Status deleteById(int id) {
		
		Optional <Customer>customerOptional = customers.stream().filter(c -> c.getId() == id).findAny();
		
		if(customerOptional.isPresent()) {
			customers.remove(customerOptional.get());
			return Status.SUCCESS;
		}
		
		return Status.FAILURE;
	}
}
