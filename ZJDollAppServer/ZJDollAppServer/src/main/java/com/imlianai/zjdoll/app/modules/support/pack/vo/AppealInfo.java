package com.imlianai.zjdoll.app.modules.support.pack.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "操作记录申诉信息")
public class AppealInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "游戏编号")
	private long optId;
	
	@ApiModelProperty(value = "申诉状态(0:未处理,1:已处理,2:游戏结束超过24小时 不可申诉)")
	private int status;
	
	@ApiModelProperty(value = "申诉理由")
	private String reason;

	public AppealInfo(){
		
	}
	
	public AppealInfo(long optId, int status, String reason) {
		super();
		this.optId = optId;
		this.status = status;
		this.reason = reason;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
