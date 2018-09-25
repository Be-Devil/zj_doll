package com.imlianai.dollpub.app.modules.support.exchange.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃合成请求对象")
public class ComposeReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "娃娃ID")
	private int dollId;

	public int getDollId() {
		return dollId;
	}

	public void setDollId(int dollId) {
		this.dollId = dollId;
	}
}
