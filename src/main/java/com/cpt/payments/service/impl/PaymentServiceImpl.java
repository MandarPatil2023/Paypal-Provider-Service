package com.cpt.payments.service.impl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.constant.Constant;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.dto.CreateOrderReqDTO;
import com.cpt.payments.dto.OrderDTO;
import com.cpt.payments.exception.PaypalProviderException;
import com.cpt.payments.exception.PaypalProviderExceptionHandler;
import com.cpt.payments.http.HttpClientUtil;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.paypal.PaypalErrorResponse;
import com.cpt.payments.paypal.res.Link;
import com.cpt.payments.paypal.res.OrderResponse;
import com.cpt.payments.service.helper.CaptureOrderRequestHelper;
import com.cpt.payments.service.helper.CreateOrderRequestHelper;
import com.cpt.payments.service.helper.GetOrderRequestHelper;
import com.cpt.payments.service.interfaces.PaymentService;
import com.google.gson.Gson;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	private Gson gson;
	private CreateOrderRequestHelper createOrderRequestHelper;
	private HttpClientUtil httpClientUtil;
	private CaptureOrderRequestHelper captureOrderRequestHelper;
	private GetOrderRequestHelper getOrderRequestHelper;
	
	public PaymentServiceImpl(CaptureOrderRequestHelper captureOrderRequestHelper,
								Gson gson,HttpClientUtil httpClientUtil,
								CreateOrderRequestHelper createOrderRequestHelper,
								GetOrderRequestHelper getOrderRequestHelper)
	{	
		this.getOrderRequestHelper=getOrderRequestHelper;
		this.captureOrderRequestHelper=captureOrderRequestHelper;
		this.gson=gson;
		this.httpClientUtil=httpClientUtil;
		this.createOrderRequestHelper=createOrderRequestHelper;
	}

	@Override
	public OrderDTO createOrder(CreateOrderReqDTO createOrderReqDTO) 
	{
		
		System.out.println("2 call create order service to call http api");
		
		HttpRequest httpRequest = createOrderRequestHelper.getCreateOrder(createOrderReqDTO);
	
		ResponseEntity<String> createOrderResponse = httpClientUtil.makeHttpRequest(httpRequest);
		System.out.println("order created paypal : "+createOrderResponse); 
	
		String createOrderResponseAsJson = createOrderResponse.getBody();
		
		OrderResponse resAsObj = gson.fromJson(createOrderResponseAsJson, OrderResponse.class);   // covert json string { xyz } to java object
		System.out.println("Got createOrderResponse:** resAsObj" + resAsObj);
		
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(resAsObj.getId());                            //Get id from paypal and provide it to ecom client
		orderDTO.setStatus(resAsObj.getStatus());				     //get status 

		String redirectUrl = resAsObj.getLinks().stream()
				.filter(link -> Constant.REDIRECT_URL_PAYER_ACTION.equals(link.getRel()))
				.map(Link::getHref)
				.findFirst()
				.orElse(null);										// getting payer action link and providing to ecom

		/*	 List<Link> links = payments.getLinks();
	        for (Link link : links) {
	            if ("payer-action".equals(link.getRel())) {
	                System.out.println("Payer Action Link: " + link);    
	  */         
	   
		orderDTO.setRedirectUrl(redirectUrl);
	        
		System.out.println("Returning created orderDTO: " + orderDTO);
		return  orderDTO;
	}

	@Override
	public OrderDTO captureOrder(String orderId) {
		HttpRequest httpRequest = captureOrderRequestHelper.prepareRequest(orderId);

		System.out.println("getOrder|| sending request to HttpClientUtil httpRequest: " + httpRequest);
		
		ResponseEntity<String> captureOrderResponse = httpClientUtil.makeHttpRequest(httpRequest);

		String captureOrderResponseAsJson = captureOrderResponse.getBody();
		OrderResponse resAsObj = gson.fromJson(captureOrderResponseAsJson, OrderResponse.class);

		System.out.println("Got createOrderResponse:** resAsObj" + resAsObj);

		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(resAsObj.getId());
		orderDTO.setStatus(resAsObj.getStatus());

		String redirectUrl = resAsObj.getLinks().stream()
				.filter(link -> Constant.REDIRECT_URL_PAYER_ACTION.equals(link.getRel()))
				.map(Link::getHref)
				.findFirst()
				.orElse(null);

		orderDTO.setRedirectUrl(redirectUrl);

		System.out.println("Returning created orderDTO: " + orderDTO);

		return orderDTO;
	}

	@Override
	public OrderDTO getOrder(String orderId) {
		
		HttpRequest httpRequest= getOrderRequestHelper.getOrder(orderId);
		 
		ResponseEntity<String> getOrderResponse = httpClientUtil.makeHttpRequest(httpRequest); 
		String getOrderResponseAsJson = getOrderResponse.getBody(); 
		
		
		if(getOrderResponse == null
				|| getOrderResponse.getBody()==null
				||getOrderResponse.getBody().trim().isEmpty())
		{
			// we  donot no what went wrong . as we dont have response data
			 
			//throw custom exception for this situation 
			throw new PaypalProviderException(
					ErrorCodeEnum.ERROR_CONNECTING_TO_PAYPAL.getErrorCode(),
					ErrorCodeEnum.ERROR_CONNECTING_TO_PAYPAL.getErrorMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR
					);

		}
		
		if(getOrderResponse.getStatusCode() != HttpStatus.OK)
		{
			//failed
			//throw custom exception for this situation 
			
			if(getOrderResponse.getStatusCode().is4xxClientError() 
					|| getOrderResponse.getStatusCode().is5xxServerError())
			{
				//json error come from paypal
				PaypalErrorResponse resAsObj = gson.fromJson(getOrderResponseAsJson, PaypalErrorResponse.class); 
				
				String errorCode;
				String errorMessage;
				if(resAsObj.getError()!=null) //paypal will throw to kind of exception
				{      
					errorCode = resAsObj.getError();
					errorMessage =resAsObj.getError_description();
				}
				else
				{
					errorCode = resAsObj.getName();
					errorMessage = resAsObj.getMessage();
				}
				
				throw new PaypalProviderException(
						errorCode,
						errorMessage,
						HttpStatus.valueOf(getOrderResponse.getStatusCode().value()));//Status value from paypal
			}
			//non 200 ,but its not 4xx or 5xx
			
			throw new PaypalProviderException(
					ErrorCodeEnum.INVALID_RESPONSE_FROM_PAYPAL.getErrorCode(),
					ErrorCodeEnum.INVALID_RESPONSE_FROM_PAYPAL.getErrorMessage(),
					HttpStatus.BAD_GATEWAY
					);		
		}
		
		//success - only 200
		OrderResponse resAsObj = gson.fromJson(getOrderResponseAsJson, OrderResponse.class); 
		 
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(resAsObj.getId());
		orderDTO.setStatus(resAsObj.getStatus());
 
		String redirectUrl = resAsObj.getLinks().stream()
				.filter(link -> Constant.REDIRECT_URL_PAYER_ACTION.equals(link.getRel()))
				.map(Link::getHref)
				.findFirst()
				.orElse(null);

		orderDTO.setRedirectUrl(redirectUrl);
		
		
		return orderDTO;
	}
}
















