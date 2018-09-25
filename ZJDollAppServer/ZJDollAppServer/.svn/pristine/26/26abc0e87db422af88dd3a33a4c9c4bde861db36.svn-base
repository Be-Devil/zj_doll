package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class BusAbandonReqVo extends BaseReqVO{

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("娃娃机id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer busId;
	@ApiModelProperty("操作id")
	private long optId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}
	
	
}
