package com.imlianai.zjdoll.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("用户注册请求")
public class UserRegisterReqVO extends BaseReqVO {

	@ApiModelProperty("手机号码")
	private String number;

	@ApiModelProperty("临时用户id,暂时不启用")
	private Long uuid;

	@ApiModelProperty("验证码")
	private int checkCode;

	@ApiModelProperty("密码")
	private String pwd;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public int getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(int checkCode) {
		this.checkCode = checkCode;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
