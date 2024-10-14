package com.cpt.payments.paypal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaypalErrorResponse {
	private String name;
	private String message;
	
	private String error;
	private String error_description;
	
	

}
