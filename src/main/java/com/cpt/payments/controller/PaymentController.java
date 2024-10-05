package com.cpt.payments.controller;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		System.out.println("1 Json Body from ecommerce URL : "+createOrder);
																							// Y yObject = modelMapper.map(xObject, Y.class);
		CreateOrderReqDTO reqDTO = modelMapper.map(createOrder, CreateOrderReqDTO.class);  // convert PaypalProviderCreateOrderReq(x) object 
		System.out.println("1 Convert to DTO object : "+reqDTO);			  //convert pojo to dto
		
		System.out.println("1 calling payment service impl create order method");
		OrderDTO responseDTO=paymentService.createOrder(reqDTO);												//dto
		System.out.println("1. recived res from service responseDTO :"+responseDTO);

		Order order=modelMapper.map(responseDTO, Order.class);                    //convert again dto to pojo object
		System.out.println("1. order created : "+order);
	
		return order;
	
	}
	
	@PostMapping("/{id}/capture")	
	public Order captureOrder(@PathVariable String id)
	{
		System.out.println("order id for capture order "+id);
		
		OrderDTO responseDTO = paymentService.captureOrder(id);
		System.out.println("Recived response from Service responseDTO:" + responseDTO);
		
		Order order = modelMapper.map(responseDTO, Order.class);
		System.out.println("Converted service response to POJO & returning order:" + order);
		
		return order;
	}

	@GetMapping("/{id}")
	public Order getOrder(@PathVariable String id)
	{
		OrderDTO responseDTO = paymentService.getOrder(id);
		
		Order order =modelMapper.map(responseDTO, Order.class);
		
		
		return order;
	}
	
}



























