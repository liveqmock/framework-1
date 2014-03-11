package com.mopon.webservice.impl;

import javax.ws.rs.PathParam;

import com.mopon.entity.Order;
import com.mopon.webservice.IOrderProcess;

public class OrderProcessImpl implements IOrderProcess {
	
	public Order getOrder(@PathParam("id") String id) {
		System.out.println(id);
		Order order = new Order();
		order.setOrderId("xabc9321231");
		order.setOrderMsg("测试webservice");
		return order;
	}

}
