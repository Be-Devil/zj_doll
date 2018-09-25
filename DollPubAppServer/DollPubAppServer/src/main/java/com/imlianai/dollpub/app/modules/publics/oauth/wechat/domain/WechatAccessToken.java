package com.imlianai.dollpub.app.modules.publics.oauth.wechat.domain;

import com.imlianai.rpc.support.common.BaseModel;

public class WechatAccessToken extends BaseModel{

	private String access_token;
	
	private int expires_in;
	
	private String errmsg;
	
	private int errcode;

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	
	
}
