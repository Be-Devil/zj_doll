package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.domain.redpacket.UserRedpacketLog;

import io.swagger.annotations.ApiModelProperty;

public class RedpacketLogRes implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("明细描述")
	private String remark;

	@ApiModelProperty("明细备注")
	private String desc;
	
	@ApiModelProperty("时间")
	private long time;
	
	@ApiModelProperty("金额")
	private double amt;
	
	public RedpacketLogRes() {
		
	}

	public RedpacketLogRes(UserRedpacketLog log) {
		this.remark = log.getRemark();
		this.time = log.getCreateTime().getTime();
		this.amt = log.getAmount();
		if(log.getOptId() > 0) {
			this.desc = "游戏编号：" + log.getOptId();
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}
}
