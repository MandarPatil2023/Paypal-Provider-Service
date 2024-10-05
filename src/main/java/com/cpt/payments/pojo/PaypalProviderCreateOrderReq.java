package com.cpt.payments.pojo;

import lombok.Data;


@Data

public class PaypalProviderCreateOrderReq {
	
	private String txnRef;
	private String currencyCode;
	private String amountValue;
	private String brandName;
	private String locale;
	private String returnUrl;
	private String cancelUrl;
	
	/*                                                              using lombok and @data annotation 
	public String getTxnRef() {
		return txnRef;
	}
	public void setTxnRef(String txnRef) {
		this.txnRef = txnRef;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getAmountValue() {
		return amountValue;
	}
	public void setAmountValue(String amountValue) {
		this.amountValue = amountValue;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCancelUrl() {
		return cancelUrl;
	}
	public void setCancelUrl(String cancelUrl) {
		this.cancelUrl = cancelUrl;
	}
	@Override
	public String toString() {
		return "PaypalProviderCreateOrderReq [txnRef=" + txnRef + ", currencyCode=" + currencyCode + ", amountValue="
				+ amountValue + ", brandName=" + brandName + ", locale=" + locale + ", returnUrl=" + returnUrl
				+ ", cancelUrl=" + cancelUrl + "]";
	}
	
	*/
	
	

}
