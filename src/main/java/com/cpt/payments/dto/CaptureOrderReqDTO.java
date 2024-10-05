package com.cpt.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptureOrderReqDTO {

	private String captureOrderId;
	//private String authToken;
	
}
