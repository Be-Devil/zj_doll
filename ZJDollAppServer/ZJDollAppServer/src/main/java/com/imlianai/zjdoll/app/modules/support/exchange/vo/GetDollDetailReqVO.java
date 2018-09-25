package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃详情信息请求对象")
public class GetDollDetailReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "娃娃ID")
	private int id;

	@ApiModelProperty(value = "娃娃类型(0:可合成，1:可兑换)")
	private int type;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
