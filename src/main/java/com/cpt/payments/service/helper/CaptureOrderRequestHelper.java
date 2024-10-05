package com.cpt.payments.service.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.service.TokenService;

@Service
public class CaptureOrderRequestHelper {
	
	private TokenService tokenService;
	
	@Value("${paypal.clientId}")
	private String clientId;
	
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	@Value("${paypal.captureOrder.url}")
	private String captureUrl;

	
	public CaptureOrderRequestHelper(TokenService tokenService)
	{
		this.tokenService=tokenService;
	}
	
		public HttpRequest prepareRequest(String orderId) {
		
		
			System.out.println("CaptureOrderRequestHelper preparing Request "
					+ "orderId:" + orderId);
			
			HttpRequest httpRequest = new HttpRequest();

			//captureUrl = captureUrl.replace("{id}", orderId); 	not changing id value dynamically,previous id was going in http call
			
			String url=String.format(captureUrl,orderId);
			
			httpRequest.setUrl(url);
			
			httpRequest.setMethod(HttpMethod.POST);

			HttpHeaders headers = new HttpHeaders();
			
			String accessToken = tokenService.getAccessToken();
			System.out.println("Got access token: " + accessToken);
			
			headers.setBearerAuth(accessToken);
			headers.setContentType(MediaType.APPLICATION_JSON);


			httpRequest.setHttpHeaders(headers);

			String reqAsJson = null;
			
			httpRequest.setRequest(reqAsJson);
			
			System.out.println("CaptureOrderRequestHelper:: Prepared Request:" + httpRequest);
			
			return httpRequest;
		
	}
}
