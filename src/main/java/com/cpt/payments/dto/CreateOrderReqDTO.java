package com.cpt.payments.dto;

import lombok.Data;

@Data
public class CreateOrderReqDTO {
	
	
	
	private String txnRef;
	private String currencyCode;
	private String amountValue;
	private String brandName;
	private String locale;
	private String returnUrl;
	private String cancelUrl;
	

}
