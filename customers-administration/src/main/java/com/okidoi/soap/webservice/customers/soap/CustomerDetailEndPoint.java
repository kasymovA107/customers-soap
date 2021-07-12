package com.okidoi.soap.webservice.customers.soap;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import br.com.okidoi.CustomerDetail;
import br.com.okidoi.GetCustomerDetailRequest;
import br.com.okidoi.GetCustomerDetailResponse;


@Endpoint  //Faz com que a classe já possa receber requisições, processá-las e enviar a resposta
public class CustomerDetailEndPoint {
	
	
	
	@PayloadRoot(namespace = "http://okidoi.com.br", localPart = "GetCustomerDetailRequest") //Prepara o metodo para receber o xml
	@ResponsePayload  // transforma o objeto Java em XML para devolver como resposta
	public GetCustomerDetailResponse processCustomerDetailRequest(@RequestPayload GetCustomerDetailRequest req) {
		
		GetCustomerDetailResponse response = new GetCustomerDetailResponse();
		
		//Mocando uma resposta fixa
		CustomerDetail cd = new CustomerDetail();
		cd.setId(1);
		cd.setName("Marcio");
		cd.setPhone("93939393939");
		cd.setEmail("teste@gmail.com");
		response.setCustomerDetail(cd);
		
		return response;
	}

}
