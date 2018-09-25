package com.imlianai.dollpub.app.modules.support.event.newyearRecharge20180207.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@ApiModel(value = "获取新春充值活动状态信息返回对象")
public class GetStatusRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "活动状态(0:未开始，1:活动期间，2:已结束)")
	private int status;
	
	@ApiModelProperty(value = "剩余时间(s)")
	private long residueTime;
	
	public GetStatusRespVO() {
		
	}
	
	public GetStatusRespVO(int status, long residueTime) {
		this.status = status;
		this.residueTime = residueTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getResidueTime() {
		return residueTime;
	}

	public void setResidueTime(long residueTime) {
		this.residueTime = residueTime;
	}
}
