package com.imlianai.zjdoll.app.modules.support.repair.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class RepairReqVo extends BaseReqVO{

	@ApiModelProperty("用户id")
	private Long uid;
	@ApiModelProperty("娃娃机id")
	private int busId;
	@ApiModelProperty("报修原因")
	private String reason;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
