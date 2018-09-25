package com.imlianai.zjdoll.app.modules.support.shipping.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "手机验证返回对象")
public class PhonebindRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手机号码")
	private long phone;

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}
}
