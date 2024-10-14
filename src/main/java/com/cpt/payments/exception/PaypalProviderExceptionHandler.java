package com.cpt.payments.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cpt.payments.pojo.ErrorResponse;

@ControllerAdvice
public class PaypalProviderExceptionHandler {                                              //throw exception will come here

    @ExceptionHandler(PaypalProviderException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(PaypalProviderException ex) {

   	ErrorResponse errorResponseDTO = new ErrorResponse(ex.getErrorCode(),ex.getMessage());
    	
        return new ResponseEntity<>(errorResponseDTO, ex.getHttpStatus());
    }
}