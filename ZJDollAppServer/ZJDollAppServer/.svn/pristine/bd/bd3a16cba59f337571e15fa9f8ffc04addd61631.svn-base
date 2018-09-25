package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class TwistedEggInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "扭蛋ID")
	private int teggId;
	
	@ApiModelProperty(value = "状态(-1:不存在，0:未领取，1:已领取，2:已过期)")
	private int status;
	
	@ApiModelProperty(value = "充值金额")
	private double amt;
	
	public TwistedEggInfo() {
		
	}
	
	public TwistedEggInfo(int tEggId, int status, double amt) {
		this.teggId = tEggId;
		this.status = status;
		this.amt = amt;
	}

	public int getTeggId() {
		return teggId;
	}

	public void setTeggId(int teggId) {
		this.teggId = teggId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}
}
