package com.cpt.payments.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.service.TokenService;

@Service
public class GetOrderRequestHelper {
	
	@Value("${paypal.clientId}")
	private String clientId;
	
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	@Value("${paypal.showOrder.url}")
	private String showOrderUrl;
	
	private TokenService tokenService;
	
	
	public GetOrderRequestHelper(TokenService tokenService)
	{
		this.tokenService=tokenService;
	}
	
	
	public HttpRequest getOrder(String orderId)
	{

		System.out.println("getOrderRequestHelper preparing Request "
				+ "orderId:" + orderId);
		
		HttpRequest httpRequest = new HttpRequest();

		String url=String.format(showOrderUrl,orderId);
		httpRequest.setUrl(url);
		
		httpRequest.setMethod(HttpMethod.GET);

		HttpHeaders headers = new HttpHeaders();
		
		String accessToken = tokenService.getAccessToken();
		
		headers.setBearerAuth(accessToken);

		httpRequest.setHttpHeaders(headers);
		httpRequest.setRequest(null);
		
		System.out.println("getOrderRequestHelper:: Prepared Request:" + httpRequest);
		
		return httpRequest;
	}

}
