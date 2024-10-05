package com.cpt.payments.paypal.req;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceContext {
	
	@SerializedName("payment_method_preference")
    private String paymentMethodPreference;
	
	@SerializedName("brand_name")
    private String brandName;
   
	private String locale;
   
    @SerializedName("landing_page")
    private String landingPage;
   
    @SerializedName("shipping_preference")
    private String shippingPreference;
  
    @SerializedName("user_action")
    private String userAction;
  
    @SerializedName("return_url")
    private String returnUrl;
  
    @SerializedName("cancel_url")
    private String cancelUrl;

}
