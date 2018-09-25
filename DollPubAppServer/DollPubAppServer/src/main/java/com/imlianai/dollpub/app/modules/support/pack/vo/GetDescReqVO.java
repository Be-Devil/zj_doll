package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取娃娃的温馨提示请求对象")
public class GetDescReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "娃娃id", required = true)
	private int dollId;

	public int getDollId() {
		return dollId;
	}

	public void setDollId(int dollId) {
		this.dollId = dollId;
	}
}
