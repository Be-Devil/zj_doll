package com.imlianai.dollpub.app.modules.core.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

@ApiModel(value = "娃娃设置")
public class DollSettingReqVO extends BaseSignReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机器id", required = true)
	private int busId;
	@ApiModelProperty(value = "设置类型 1-游戏时间 2-概率 3-礼品出口", required = true)
	private int type;
	@ApiModelProperty(value = "设置值，游戏时间范围为5-60秒；概率范围为1-250；礼品出口0-左前 1-左后", required = true)
	private int value;
	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
