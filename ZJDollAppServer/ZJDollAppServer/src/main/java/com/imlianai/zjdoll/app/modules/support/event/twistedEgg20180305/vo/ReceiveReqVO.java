package com.imlianai.zjdoll.app.modules.support.event.twistedEgg20180305.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModelProperty;

public class ReceiveReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "扭蛋ID", required = true)
	private int teggId;

	public int getTeggId() {
		return teggId;
	}

	public void setTeggId(int teggId) {
		this.teggId = teggId;
	}
}
