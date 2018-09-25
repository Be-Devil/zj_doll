package com.imlianai.dollpub.app.modules.support.pack.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃领取请求对象")
public class ReceiveReqVO extends GetDollDetailReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "手机号码(需填写时传值)")
	private long phone;
	
	@ApiModelProperty(value = "备注(需填写时传值)")
	private String remark;
	
	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
