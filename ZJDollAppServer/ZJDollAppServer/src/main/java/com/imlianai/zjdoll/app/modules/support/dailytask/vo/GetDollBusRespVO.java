package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import com.imlianai.zjdoll.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取随机一台娃娃机信息返回对象")
public class GetDollBusRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("娃娃机信息")
	private DollBus dollBus;

	public DollBus getDollBus() {
		return dollBus;
	}

	public void setDollBus(DollBus dollBus) {
		this.dollBus = dollBus;
	}
}
