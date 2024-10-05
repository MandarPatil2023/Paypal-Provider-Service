package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaypalProviderCaptureOrderReq {

	private String captureOrderId;
	//private String authToken;
	
}
