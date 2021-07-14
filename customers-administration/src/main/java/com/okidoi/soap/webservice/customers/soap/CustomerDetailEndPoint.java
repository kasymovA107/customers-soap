package com.okidoi.soap.webservice.customers.soap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.okidoi.soap.webservice.customers.bean.Customer;
import com.okidoi.soap.webservice.customers.service.CustomerDetailService;

import br.com.okidoi.CustomerDetail;
import br.com.okidoi.DeleteCustomerResponse;
import br.com.okidoi.GetAllCustomerDetailRequest;
import br.com.okidoi.DeleteCustomerRequest;
import br.com.okidoi.GetAllCustomerDetailResponse;
import br.com.okidoi.GetCustomerDetailRequest;
import br.com.okidoi.GetCustomerDetailResponse;


@Endpoint  //Faz com que a classe já possa receber requisições, processá-las e enviar a resposta
public class CustomerDetailEndPoint {
	
	@Autowired
	CustomerDetailService service;
	
	
	@PayloadRoot(namespace = "http://okidoi.com.br", localPart = "GetCustomerDetailRequest") //Prepara o metodo para receber o xml
	@ResponsePayload  // transforma o objeto Java em XML para devolver como resposta
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) throws Exception {
		
		Customer customer = service.findById(req.getId());
		if(customer == null) {
			throw new Exception("Invalid Customer Id" + req.getId());
		}
		
		return convertToCustomerDetailResponse(customer);
	}
	
	private GetCustomerDetailResponse convertToCustomerDetailResponse(Customer customer) {
		
		GetCustomerDetailResponse resp = new GetCustomerDetailResponse();
		resp.setCustomerDetail(convertToCustomerDetail(customer));
		return resp;
		
	}	
	
	private CustomerDetail convertToCustomerDetail(Customer customer) {
		
		CustomerDetail customerDetail = new CustomerDetail();
		customerDetail.setId(customer.getId());
		customerDetail.setEmail(customer.getEmail());
		customerDetail.setName(customer.getName());
		customerDetail.setPhone(customer.getPhone());
		
		return customerDetail;
		
	}
	
	
	@PayloadRoot(namespace = "http://okidoi.com.br", localPart = "GetAllCustomerDetailRequest") //Prepara o metodo para receber o xml
	@ResponsePayload  // transforma o objeto Java em XML para devolver como resposta
	public GetAllCustomerDetailResponse processGetAllCustomerDetailRequest(@RequestPayload GetAllCustomerDetailRequest req){
	
		
		List<Customer> customers = service.findAll();
		return convertToGetAllCustomerDetailResponse(customers);
		
	}
	
	
	private GetAllCustomerDetailResponse convertToGetAllCustomerDetailResponse(List<Customer> customers) {
		
		GetAllCustomerDetailResponse resp = new GetAllCustomerDetailResponse();
		customers.stream().forEach(c -> resp.getCustomerDetail().add(convertToCustomerDetail(c)));
		return resp;
	}
	
	
	@PayloadRoot(namespace = "http://okidoi.com.br", localPart = "DeleteCustomerRequest") //Prepara o metodo para receber o xml
	@ResponsePayload  // transforma o objeto Java em XML para devolver como resposta
	public DeleteCustomerResponse deleteCustomerRequest(@RequestPayload DeleteCustomerRequest req) throws Exception {
		
		DeleteCustomerResponse  resp = new DeleteCustomerResponse();
		resp.setStatus(convertStatusSoap(service.deleteById(req.getId())));
		return resp;
	}	
	
	private br.com.okidoi.Status convertStatusSoap(
			com.okidoi.soap.webservice.customers.helper.Status statusService){

		if(statusService == com.okidoi.soap.webservice.customers.helper.Status.FAILURE ) {
			return  br.com.okidoi.Status.FAILURE;
		}else {
			return  br.com.okidoi.Status.SUCCESS;
		}
	} 
	

}
