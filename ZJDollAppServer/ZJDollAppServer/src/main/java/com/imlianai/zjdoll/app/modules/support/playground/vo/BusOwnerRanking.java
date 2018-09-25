package com.imlianai.zjdoll.app.modules.support.playground.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.user.UserGeneral;

import io.swagger.annotations.ApiModelProperty;

public class BusOwnerRanking implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户UID")
	private Long uid;
	
	@ApiModelProperty(value = "用户昵称")
	private String uname;
	
	@ApiModelProperty(value = "用户头像")
	private String uhead;
	
	@ApiModelProperty(value = "奖励描述")
	private String rewardDesc;
	
	public BusOwnerRanking() {
		
	}
	
	public BusOwnerRanking(String rewardDesc, String uname, String uhead) {
		this.rewardDesc = rewardDesc;
		this.uname = uname;
		this.uhead = uhead;
	}

	public BusOwnerRanking(UserGeneral userGeneral, String rewardDesc) {
		if(userGeneral != null) {
			this.uid = userGeneral.getUid();
			this.uname = userGeneral.getName();
			this.uhead = userGeneral.getHead();
		}
		this.rewardDesc = rewardDesc;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
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

	public String getRewardDesc() {
		return rewardDesc;
	}

	public void setRewardDesc(String rewardDesc) {
		this.rewardDesc = rewardDesc;
	}
}
