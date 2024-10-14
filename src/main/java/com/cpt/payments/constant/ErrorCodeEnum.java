package com.cpt.payments.constant;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCodeEnum {
	
	
	GENERIC_ERROR("30000", "Unable to process request please try again"),
	ERROR_CONNECTING_TO_PAYPAL("30001","Hmac Signature is missing in request Header"),
	INVALID_RESPONSE_FROM_PAYPAL("30002","Invalid response from paypal");
	
	
	private final String errorCode;
	private final String errorMessage;
	
    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
