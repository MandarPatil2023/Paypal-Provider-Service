package com.cpt.payments.paypal;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
	
		@SerializedName("scope")
	    private String scope;

	    @SerializedName("access_token")
	    private String accessToken;

	    @SerializedName("token_type")
	    private String tokenType;

	    @SerializedName("app_id")
	    private String appId;

	    @SerializedName("expires_in")
	    private int expiresIn;

	    @SerializedName("nonce")
	    private String nonce;

}
