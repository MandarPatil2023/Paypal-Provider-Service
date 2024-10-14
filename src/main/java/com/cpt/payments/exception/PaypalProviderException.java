package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PaypalProviderException extends RuntimeException {

	private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public PaypalProviderException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);       //setting the error message as exception message
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}