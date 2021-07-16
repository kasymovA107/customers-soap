package com.okidoi.soap.webservice.customers.soap;

import java.util.Collections;
import java.util.List;



import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;



@Configuration  //Informa ao Spring que a classe é um arquivo de configuração
@EnableWs  		//Habilitar o SpringWebService
public class WebServiceConfig extends WsConfigurerAdapter{

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		
		//Basicamente essa Servlet trata todas as requisições
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		messageDispatcherServlet.setApplicationContext(context);		
		messageDispatcherServlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
		
	}
	
	@Bean
	public XsdSchema customerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customer-information.xsd"));
	}
	
	@Bean(name="customers")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema customerSchema) {
		
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CustomerPort");  //Como uma interface
		definition.setTargetNamespace("http://okidoi.com.br");
		definition.setLocationUri("/ws");
		definition.setSchema(customerSchema);
		return definition;
	} 
	
	@Bean
	public XwsSecurityInterceptor securityInterceptor() {  //Interceptará todas as solicitações que chegarem e verificará se é segura. (Se o usuário e senha passados são validos)
		
		XwsSecurityInterceptor  securityInterceptor = new XwsSecurityInterceptor ();
		securityInterceptor.setCallbackHandler(callbackHandler());
		securityInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml")); //pega do xml em src/main/resources
		return securityInterceptor;
	}
	
	
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler() {
		
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		handler.setUsersMap(Collections.singletonMap("admin", "changeit"));
		return handler;
		
	}
	
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
			
		interceptors.add(securityInterceptor());  //adiciona o nosso securityInterceptor
	}
	
	
}
