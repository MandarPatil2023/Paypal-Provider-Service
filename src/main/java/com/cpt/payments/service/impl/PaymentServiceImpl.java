package com.cpt.payments.service.impl;

import org.springframework.stereotype.Service;

import com.cpt.payments.dto.CreateOrderReqDTO;
import com.cpt.payments.dto.OrderDTO;
import com.cpt.payments.service.TokenService;
import com.cpt.payments.service.interfaces.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private TokenService tokenService;
	public PaymentServiceImpl(TokenService tokenService)
	{	
		
		this.tokenService=tokenService;
	}

	@Override
	public OrderDTO createOrder(CreateOrderReqDTO createOrderReqDTO) 
	{
		String accestoken=tokenService.getAccessToken();
		System.out.println("acces_token"+accestoken);
		
		OrderDTO Order=new OrderDTO();
		
		Order.setId("1234");
		Order.setStatus("completed");
		Order.setRedirectUrl("http://localhost:8080/v1/paypal/order/1234");
		System.out.println("order : "+Order);
		
		// TODO Auto-generated method stub
		return Order;
	}
	
	
	
}
