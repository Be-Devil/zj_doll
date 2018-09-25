package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.user.UserGeneral;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("萌主基本信息")
public class BusOwnerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户UID")
	private long uid;
	
	@ApiModelProperty(value = "用户昵称")
	private String uname;
	
	@ApiModelProperty(value = "用户头像")
	private String uhead;
	
	public BusOwnerInfo() {
		
	}

	public BusOwnerInfo(UserGeneral userGeneral) {
		if(userGeneral != null) {
			this.uid = userGeneral.getUid();
			this.uname = userGeneral.getName();
			this.uhead = userGeneral.getHead();
		}
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
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
}
