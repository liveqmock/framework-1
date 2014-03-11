package com.mopon.webservice;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.ws.rs.PathParam;

import com.mopon.entity.Order;

@WebService(targetNamespace="http://www.ws.com/GreetService",portName="GreetPort")
public interface IOrderProcess {
	
	public Order getOrder(@PathParam("id") String orderId);
	
}
