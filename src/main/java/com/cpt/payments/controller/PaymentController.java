package com.cpt.payments.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.pojo.Order;
import com.cpt.payments.pojo.PaypalProviderCreateOrderReq;

@RestController
@RequestMapping("/v1/paypal/order")
public class PaymentController {
	
	@PostMapping
	public Order createOrder(@RequestBody PaypalProviderCreateOrderReq createOrder)
	{
		System.out.println("order created : "+createOrder);
		
		Order order=new Order();
		order.setId("1234");
		order.setStatus("completed");
		order.setRedirectUrl("http://localhost:8080/v1/paypal/order/1234");
		
		return order;
		
		
	/*	return Order.builder()                                       //res to processing service
				.id("1234")
				.status("created")
				.redirectUrl("http://localhost:8080/v1/paypal/order/1234")
				.build();
	*/
	}

}
