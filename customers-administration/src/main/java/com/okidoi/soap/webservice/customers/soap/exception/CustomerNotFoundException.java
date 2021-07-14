package com.okidoi.soap.webservice.customers.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//@SoapFault(faultCode = FaultCode.CLIENT)
@SoapFault(faultCode = FaultCode.CUSTOM, customFaultCode = "{http://okidoi.com.br}001_Customer_Not_Found")  //Personalizar com o codigo de erro de uma tabela por exemplo
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerNotFoundException(String message) {
		super(message);
	}
	
	

}
