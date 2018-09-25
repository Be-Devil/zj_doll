package com.imlianai.zjdoll.app.modules.support.userdoll.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取物流信息请求对象")
public class GetLogisticsReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户娃娃ID")
	private Long udollId;

	public Long getUdollId() {
		return udollId;
	}

	public void setUdollId(Long udollId) {
		this.udollId = udollId;
	}
}
