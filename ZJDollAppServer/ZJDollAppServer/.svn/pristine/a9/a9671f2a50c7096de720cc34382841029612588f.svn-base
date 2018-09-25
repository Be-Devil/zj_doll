package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "抽奖请求对象")
public class PlayReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "类型(0:使用金币抽奖，1:使用任务扭蛋抽奖)", required = true)
	private int type;
	
	@ApiModelProperty(value = "连抽标志(0:否，1:是)")
	private int flag;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
}
