package com.imlianai.dollpub.app.modules.core.user.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class CustomerAuthToken extends BaseModel{

	private int authCustomerId;
	private String srcUid;
	private String token;
	
	private long innerUid;

	public int getAuthCustomerId() {
		return authCustomerId;
	}

	public void setAuthCustomerId(int authCustomerId) {
		this.authCustomerId = authCustomerId;
	}

	public String getSrcUid() {
		return srcUid;
	}

	public void setSrcUid(String srcUid) {
		this.srcUid = srcUid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getInnerUid() {
		return innerUid;
	}

	public void setInnerUid(long innerUid) {
		this.innerUid = innerUid;
	}

}
