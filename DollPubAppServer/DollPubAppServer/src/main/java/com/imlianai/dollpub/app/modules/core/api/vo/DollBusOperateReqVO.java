package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃机操作请求对象")
public class DollBusOperateReqVO extends BaseSignReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机器id", required = true)
	private int busId;

	@ApiModelProperty(value = "用户id", required = true)
	private Long uid;

	@ApiModelProperty(value = "操作id", required = true)
	private Long optId;
//【5】开始游戏，
	@ApiModelProperty(value = "机器操作指令:【1】向前移动，【2】向后移动，【3】向左移动，【4】向右移动，【6】下抓，【7】停止移动", required = true)
	private int type;

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getOptId() {
		return optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
