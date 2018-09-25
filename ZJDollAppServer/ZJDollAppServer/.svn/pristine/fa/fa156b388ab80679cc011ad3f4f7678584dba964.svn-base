package com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.imlianai.rpc.support.utils.DateUtils;


public class WebWeixinToken {
	/**
	 * access_toke / jsapi_ticket 
	 */
	private String appid;
	private String token_ticket;
	private String update_time;
	private int expires_in;
	private int type;
	
	public WebWeixinToken() {
	}
	
	public WebWeixinToken(String appid,String token_ticket,int expires_in,int type) {
		this.appid = appid;
		this.token_ticket = token_ticket;
		this.update_time = DateUtils.getNowTime();
		this.expires_in = expires_in;
		this.type = type;
	}
	
	public WebWeixinToken(ResultSet rs) throws SQLException {
		this.appid = rs.getString("appid");
		this.token_ticket = rs.getString("token_ticket");
		this.update_time = DateUtils.timestamp2Minutes(rs.getTimestamp("update_time"));
		this.expires_in = rs.getInt("expires_in");
		this.type = rs.getInt("type");
	}

	public String getToken_ticket() {
		return token_ticket;
	}

	public void setToken_ticket(String token_ticket) {
		this.token_ticket = token_ticket;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	@Override
	public String toString() {
		return "WebWeixinToken [appid=" + appid + ", token_ticket="
				+ token_ticket + ", update_time=" + update_time
				+ ", expires_in=" + expires_in + ", type=" + type + "]";
	}
	
	
}
