package com.imlianai.zjdoll.app.modules.core.trade.catalog.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

/**
 * 用户日充值记录
 * @author cls
 *
 */
public class ChargeDayRecord extends BaseModel{

	private static final long serialVersionUID = 1L;

	private Long uid;
	/**
	 * 日期code
	 */
	private int dateCode;
	/**
	 * 充值金额
	 */
	private double cost;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getDateCode() {
		return dateCode;
	}

	public void setDateCode(int dateCode) {
		this.dateCode = dateCode;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
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
