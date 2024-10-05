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
public class PurchaseUnit {
	
	@SerializedName("reference_id")
    private String referenceId;
	
    private Amount amount;
}
