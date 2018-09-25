package com.imlianai.zjdoll.app.modules.publics.weixinAccount.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.imlianai.rpc.support.manager.aspect.annotations.FormatTime;
import com.imlianai.rpc.support.utils.DateUtils;


public class WebUserInfoToken {
	private String openid;
	private String access_token;
	private int expires_in;
	private String refresh_token;
	@FormatTime
	private String update_time;
	
	public WebUserInfoToken() {
		
	}
	
	public WebUserInfoToken(ResultSet rs) throws SQLException {
		this.openid = rs.getString("openid");
		this.access_token = rs.getString("access_token");
		this.refresh_token = rs.getString("refresh_token");
		this.expires_in = rs.getInt("expires_in");
		this.update_time = DateUtils.timestamp2Minutes(rs.getTimestamp("update_time"));
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

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

	public String getRefresh_token() {
		return refresh_token;
	}

	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	

	
}
