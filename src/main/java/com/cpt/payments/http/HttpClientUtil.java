package com.cpt.payments.http;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    		
    		System.out.println("HttpClientUtil http request : "+httpRequest);
    		

    		HttpEntity<Object> entity = new HttpEntity<>(httpRequest.getRequest(), httpRequest.getHttpHeaders());
    		
    		
    		// Make HTTP call using RestTemplate
    		ResponseEntity<String> httpResponseObj = restTemplate.exchange(httpRequest.getUrl(), httpRequest.getMethod(), entity, String.class);
    		
    		System.out.println("HttpResponse obj "+httpResponseObj);
			return httpResponseObj;
    	} 
    	catch (HttpClientErrorException | HttpServerErrorException ex) {
    		ex.printStackTrace();
    		// Handle client or server errors
    		System.out.println("HTTP error: " + ex.getStatusCode() + " " + ex.getResponseBodyAsString());
    		return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
    	} 
    	catch (Exception ex) {
    		ex.printStackTrace();
    		// Handle any other exceptions
    		System.out.println("General error: " + ex.getMessage());
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    	}
    }
}
