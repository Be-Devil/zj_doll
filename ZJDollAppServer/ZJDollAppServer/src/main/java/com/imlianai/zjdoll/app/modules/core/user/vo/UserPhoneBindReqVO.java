package com.imlianai.zjdoll.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("用户手机绑定请求")
public class UserPhoneBindReqVO extends BaseReqVO {

	@ApiModelProperty("手机号码")
	private String number;

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("验证码")
	private int checkCode;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(int checkCode) {
		this.checkCode = checkCode;
	}
	
}
