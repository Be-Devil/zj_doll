package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取可领取活跃度的任务数量返回对象")
public class GetCountRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("可领取活跃度的任务数量")
	private int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
