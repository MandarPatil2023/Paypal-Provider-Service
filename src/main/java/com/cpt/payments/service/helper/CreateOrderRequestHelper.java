package com.cpt.payments.service.helper;


import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.cpt.payments.constant.Constant;
import com.cpt.payments.dto.CreateOrderReqDTO;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.paypal.req.Amount;
import com.cpt.payments.paypal.req.ExperienceContext;
import com.cpt.payments.paypal.req.PayPal;
import com.cpt.payments.paypal.req.PaymentRequest;
import com.cpt.payments.paypal.req.PaymentSource;
import com.cpt.payments.paypal.req.PurchaseUnit;
import com.cpt.payments.service.TokenService;
import com.google.gson.Gson;

@Component
public class CreateOrderRequestHelper {
	
	

	private Gson gson;
	
	private TokenService tokenService;
	
	@Value("${paypal.clientId}")
	private String clientId;
	
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	@Value("${paypal.createOrder.url}")
	private String createUrl;

	
	public CreateOrderRequestHelper(Gson gson,TokenService tokenService)
	{
		this.gson=gson;
		this.tokenService=tokenService;
		
	}
	
	public  HttpRequest getCreateOrder(CreateOrderReqDTO reqDTO)
	{
		System.out.println();
		System.out.println(" calling token service to get access token");
		
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setMethod(HttpMethod.POST);    
		httpRequest.setUrl(createUrl);
		
			HttpHeaders httpHeaders =new HttpHeaders();
		
			String oauthAccesstoken = tokenService.getAccessToken();
			
			System.out.println();
			System.out.println("4 After getting token back to create code service");
			
			httpHeaders.set(Constant.USERNAME, clientId);							 // client id & client secret
			httpHeaders.set(Constant.PASSWORD, clientSecret);
			httpHeaders.setBasicAuth(clientId,clientSecret); 
        
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			httpHeaders.set(Constant.PAY_PAL_REQUEST_ID,reqDTO.getTxnRef());   //remove hard coding and add to constant class
			httpHeaders.setBearerAuth(oauthAccesstoken);
        
		httpRequest.setHttpHeaders(httpHeaders);
																													// learn more about builder patter
		Amount amount = Amount.builder()									
				.currencyCode(reqDTO.getCurrencyCode())
				.value(reqDTO.getAmountValue())
				.build();
		
		List<PurchaseUnit> listPurchaseUnit = List.of(
				PurchaseUnit.builder()
				.referenceId(reqDTO.getTxnRef())
				.amount(amount)
				.build()
				);
		
		PaymentRequest paymentRequest = PaymentRequest.builder()
				.intent(Constant.INTENT_CAPTURE)
				.purchaseUnits(listPurchaseUnit)                //listPurchaseUnit is refactored above 
				.paymentSource(PaymentSource.builder()
						.paypal(PayPal.builder()
								.experienceContext(ExperienceContext.builder()
										.shippingPreference(Constant.SHIPPING_GET_FROM_FILE)
										.landingPage(Constant.LANDING_PAGE_LOGIN)
										.userAction(Constant.USER_ACTION_PAY_NOW)
										.paymentMethodPreference(Constant.PAYMENT_METHOD_IMMEDIATE_PAYMENT_REQUIRED)
										
										.brandName(reqDTO.getBrandName())
										.locale(reqDTO.getLocale())
										.returnUrl(reqDTO.getReturnUrl())
										.cancelUrl(reqDTO.getCancelUrl())
										.build())
								.build())
						.build())
				.build();
		
		
		System.out.println("payment request : "+paymentRequest);
		String reqAsJson = gson.toJson(paymentRequest);
		System.out.println("after converting payment request to json using gson : "+reqAsJson);
		
		httpRequest.setRequest(reqAsJson);
		
		System.out.println("CreateOrderRequestHelper:: Prepared Request:" + httpRequest);
		
		return httpRequest;

	}

}
