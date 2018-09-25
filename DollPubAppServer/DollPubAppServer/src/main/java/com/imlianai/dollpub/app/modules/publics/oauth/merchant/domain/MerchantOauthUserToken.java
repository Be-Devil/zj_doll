package com.imlianai.dollpub.app.modules.publics.oauth.merchant.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class MerchantOauthUserToken extends BaseModel{

	private String userId;
	
	private String token;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
