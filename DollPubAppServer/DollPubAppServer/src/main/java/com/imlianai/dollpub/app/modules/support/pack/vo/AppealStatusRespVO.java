package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查看申诉状态返回对象")
public class AppealStatusRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "操作记录申诉信息")
	private AppealInfo appealInfo;

	public AppealInfo getAppealInfo() {
		return appealInfo;
	}

	public void setAppealInfo(AppealInfo appealInfo) {
		this.appealInfo = appealInfo;
	}
}
