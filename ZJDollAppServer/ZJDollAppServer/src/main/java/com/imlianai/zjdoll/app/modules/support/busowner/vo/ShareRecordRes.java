package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.io.Serializable;
import java.util.Random;

import com.imlianai.zjdoll.app.modules.publics.oauth.wechat.domain.UserWechat;
import com.imlianai.zjdoll.app.modules.support.busowner.enm.ConDescEnm;

import io.swagger.annotations.ApiModelProperty;

public class ShareRecordRes implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户名称")
	private String uname;
	
	@ApiModelProperty(value = "用户头像")
	private String uhead;
	
	@ApiModelProperty(value = "贡献分享值")
	private int conValue;
	
	@ApiModelProperty(value = "机子")
	private String busIdStr;
	
	@ApiModelProperty(value = "时间")
	private String time;
	
	@ApiModelProperty(value = "贡献描述")
	private String conDesc;
	
	public ShareRecordRes() {
		
	}
	
	public ShareRecordRes(UserWechat userWechat) {
		if(userWechat != null) {
			this.uname = userWechat.getName();
			this.uhead = userWechat.getHead();
		}
	}

	public ShareRecordRes(UserWechat userWechat, int conValue,
			String busIdStr, String time) {
		if(userWechat != null) {
			this.uname = userWechat.getName();
			this.uhead = userWechat.getHead();
		}
		this.conValue = conValue;
		this.busIdStr = busIdStr;
		this.time = time;
		this.conDesc = ConDescEnm.values()[new Random().nextInt(ConDescEnm.values().length)].getDesc();
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUhead() {
		return uhead;
	}

	public void setUhead(String uhead) {
		this.uhead = uhead;
	}

	public int getConValue() {
		return conValue;
	}

	public void setConValue(int conValue) {
		this.conValue = conValue;
	}

	public String getBusIdStr() {
		return busIdStr;
	}

	public void setBusIdStr(String busIdStr) {
		this.busIdStr = busIdStr;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getConDesc() {
		return conDesc;
	}

	public void setConDesc(String conDesc) {
		this.conDesc = conDesc;
	}
}
