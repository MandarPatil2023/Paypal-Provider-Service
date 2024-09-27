package com.cpt.payments.service;




import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cpt.payments.http.HttpClientUtil;
import com.cpt.payments.http.HttpRequest;

@Service
public class TokenService {
	
	private HttpClientUtil httpClientUtil;
	
	public TokenService(HttpClientUtil httpClientUtil)
	{
		this.httpClientUtil=httpClientUtil;
	}
	
	
	public String getAccessToken() 
	{	/*
		private String url; 
		private HttpMethod method;
		private Object request;
		private HttpHeaders headers;
		*/
		System.out.println("Getting access token");
		HttpRequest httpRequest = new HttpRequest();
		httpRequest.setUrl("https://api-m.sandbox.paypal.com/v1/oauth2/token");
	
		httpRequest.setMethod(HttpMethod.POST);
		
        MultiValueMap<String, String> requestPayload = new LinkedMultiValueMap<>();
        requestPayload.add("grant_type", "client_credentials");
        httpRequest.setRequest(requestPayload);
        
 
        
        HttpHeaders httpHeaders =new HttpHeaders();
        String clientId="AVnDWHiZXDFgZcCQLMuI4KS7RSYdA8G3gcLM4DxGzoZ39YyfrGucjeJ5MMIzuC2YcsYWPGYpQq_FY7Wx";
        String clientSecret="EInXeFoc9lIcP2_LXLuv560na5y-iJjkoMgsJkUOoYHBLczs70Lm33dPcdxQztoBkzwNj9nu-oLC5FGY";
       
        /*     String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;
        httpHeaders.set("Authorization", authHeader);
        
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        */  
       
        httpHeaders.set("Username", clientId);
        httpHeaders.set("Password", clientSecret);
        httpHeaders.setBasicAuth(clientId,clientSecret);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        httpRequest.setHttpHeaders(httpHeaders);
        
      
        System.out.println("Making http request call "+httpRequest);
       
        ResponseEntity<String> oauthTokenResponse=httpClientUtil.makeHttpRequest(httpRequest);
        System.out.println("Get the oauthTokenResponse : "+oauthTokenResponse);
		return "access token";
	}
}