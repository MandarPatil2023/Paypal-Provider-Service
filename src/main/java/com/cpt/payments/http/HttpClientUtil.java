package com.cpt.payments.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class HttpClientUtil  {

    private final RestTemplate restTemplate;

    public HttpClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public  ResponseEntity<String> makeHttpRequest(HttpRequest httpRequest)
    { 
    	try {
    		
    		System.out.println("5 HttpClientUtil http request : "+httpRequest);
    		
    		// Prepare request entity with headers and body
    		HttpEntity<Object> entity = new HttpEntity<>(httpRequest.getRequest(), httpRequest.getHttpHeaders());
    		
    		// Make HTTP call using RestTemplate
    		ResponseEntity<String> httpResponseObj = restTemplate.exchange(httpRequest.getUrl(), httpRequest.getMethod(), entity, String.class);
    		
    		System.out.println("6 HttpResponse obj "+httpResponseObj);
			return httpResponseObj;
    	} 
    	catch (HttpClientErrorException | HttpServerErrorException e) {
    		e.printStackTrace();
    		// Handle client or server errors
    		System.out.println("HTTP error: " + e.getStatusCode() + " " + e.getResponseBodyAsString());
    		return ResponseEntity.status(e.getStatusCode()).build();
    	} 
    	catch (Exception e) {
    		e.printStackTrace();
    		// Handle any other exceptions
    		System.out.println("General error: " + e.getMessage());
    		return ResponseEntity.status(500).build();
    	}
    }
}
