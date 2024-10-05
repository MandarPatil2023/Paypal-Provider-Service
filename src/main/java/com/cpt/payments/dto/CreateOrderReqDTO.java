package com.cpt.payments.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderReqDTO {
	private String txnRef;
	private String currencyCode;
	private String amountValue;
	private String brandName;
	private String locale;
	private String returnUrl;
	private String cancelUrl;
	

}
