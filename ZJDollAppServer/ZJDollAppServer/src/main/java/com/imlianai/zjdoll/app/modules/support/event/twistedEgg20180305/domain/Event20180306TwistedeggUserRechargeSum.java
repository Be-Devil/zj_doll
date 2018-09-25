package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

public class Event20180306TwistedeggUserRechargeSum extends BaseModel{

	private static final long serialVersionUID = 1L;
	
	private Long uid;
	/**
	 * 类型(0:普通，1:大奖品(针对平台)，2:福袋(针对平台))
	 */
	private int type;
	/**
	 * 充值金额
	 */
	private double amount;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
