package com.imlianai.zjdoll.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查看申诉状态请求对象")
public class AppealStatusReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "optId", required = true)
	private long optId;

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}
}
