package com.imlianai.zjdoll.app.modules.support.signin.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户签到返回对象")
public class SigninRespVO extends BaseRespVO{
	
	private static final long serialVersionUID = 1L;

	private SigninAwardInfo awardInfo;

	public SigninAwardInfo getAwardInfo() {
		return awardInfo;
	}

	public void setAwardInfo(SigninAwardInfo awardInfo) {
		this.awardInfo = awardInfo;
	}
}
