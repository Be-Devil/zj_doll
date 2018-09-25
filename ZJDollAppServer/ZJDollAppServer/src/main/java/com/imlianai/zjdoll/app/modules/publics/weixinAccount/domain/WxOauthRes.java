package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;

import com.imlianai.rpc.support.common.BaseModel;

@SuppressWarnings("serial")
public class WxOauthRes extends BaseModel{

	private  String unionid;
	
	private  String openid;
	
	private  String refresh_token;
	
	private  String access_token;

	public WxOauthRes(){}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
}
