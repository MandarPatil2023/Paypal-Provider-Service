package com.cpt.payments.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.cpt.payments.constant.Constant;
import com.cpt.payments.http.HttpClientUtil;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.paypal.TokenResponse;
import com.google.gson.Gson;

@Service
public class TokenService {
	

	private HttpClientUtil httpClientUtil;
	private Gson gson;
	
	private static String accessToken;	//temp static  , todo redis cache
	
	@Value("${paypal.clientId}")
	private String clientId;
	
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	@Value("${paypal.oauth.url}")
	private String oauthUrl;
	
	public TokenService(HttpClientUtil httpClientUtil,Gson gson)
	{
		this.httpClientUtil=httpClientUtil;
		this.gson=gson;
	}
	
	public String getAccessToken() 
	{	
		System.out.println();
		System.out.println("3 In Token Service");
		
		if(accessToken != null)
		{
			System.out.println("3 Returning already available token");
			return accessToken;
		}
		
		/*
		private String url; 
		private HttpMethod method;
		private Object request;
		private HttpHeaders headers;
		*/
		
		System.out.println("3 Getting access token");
		System.out.println("3 access token is null so we are making auth call.");
		
		HttpRequest httpRequest = new HttpRequest();
		//"https://api-m.sandbox.paypal.com/v1/oauth2/token"
		//url
		httpRequest.setUrl(oauthUrl);
	
		//method
		httpRequest.setMethod(HttpMethod.POST);
		
        //body
		MultiValueMap<String, String> requestPayload = new LinkedMultiValueMap<>();
        requestPayload.add(Constant.OAUTH_GRANT_TYPE, Constant.OAUTH_GRANT_CLIENT_CREDENTIALS);
        httpRequest.setRequest(requestPayload);
        
        //header
        HttpHeaders httpHeaders =new HttpHeaders();
       
        httpHeaders.set(Constant.USERNAME, clientId);
        httpHeaders.set(Constant.PASSWORD, clientSecret);
        httpHeaders.setBasicAuth(clientId,clientSecret);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        httpRequest.setHttpHeaders(httpHeaders);
        
      
        System.out.println("3 Making http request call "+httpRequest);
       
        ResponseEntity<String> oauthTokenResponse=httpClientUtil.makeHttpRequest(httpRequest);
        
        String resBodyAsJson=oauthTokenResponse.getBody();										//only body will get,all other unnecessary will be gone
        
        System.out.println("3 Get the oauthTokenResponse : "+oauthTokenResponse);
        System.out.println("3 Get the resBodyAsJson : "+resBodyAsJson);
        
        
        TokenResponse responseAsObj = gson.fromJson(resBodyAsJson, TokenResponse.class);
        System.out.println("3 Response : "+responseAsObj);
        
       accessToken=responseAsObj.getAccessToken();									//set to static
        System.out.println("3 Returning accessToken : "+accessToken);
        
        
		return accessToken;
	}
}
























