package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "提现验证金额请求对象")
public class VerifyAmtReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "提现金额", required = true)
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
