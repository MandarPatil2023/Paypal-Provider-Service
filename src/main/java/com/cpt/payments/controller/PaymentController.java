package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cpt.payments.dto.CreateOrderReqDTO;
import com.cpt.payments.dto.OrderDTO;
import com.cpt.payments.pojo.Order;
import com.cpt.payments.pojo.PaypalProviderCreateOrderReq;
import com.cpt.payments.service.interfaces.PaymentService;

@RestController
@RequestMapping("/v1/paypal/order")
public class PaymentController {
	
	
	private ModelMapper modelMapper;				//DI using constructor , autowired
	private PaymentService paymentService;
	
	public PaymentController(ModelMapper modelMapper,PaymentService paymentService)
	{
		this.modelMapper=modelMapper;
		this.paymentService=paymentService;
	}
	
	@PostMapping
	public Order createOrder(@RequestBody PaypalProviderCreateOrderReq createOrder)
	{
		System.out.println("1 order created : "+createOrder);
																							// Y yObject = modelMapper.map(xObject, Y.class);
		CreateOrderReqDTO reqDTO = modelMapper.map(createOrder, CreateOrderReqDTO.class);  // convert PaypalProviderCreateOrderReq(x) object 
		System.out.println("2 CreateOrderReqDTO created DTO reqDTO : "+reqDTO);			  //convert pojo to dto

		    
		OrderDTO responseDTO=paymentService.createOrder(reqDTO);												//dto
		System.out.println("10 recived res from service responseDTO :"+responseDTO);

		Order order=modelMapper.map(responseDTO, Order.class);                    //convert again dto to pojo object
		System.out.println("11 order created : "+order);
	
		return order;
		
		
	/*	return Order.builder()                                       //res to processing service
				.id("1234")
				.status("created")
				.redirectUrl("http://localhost:8080/v1/paypal/order/1234")
				.build();
	*/
	}

}
