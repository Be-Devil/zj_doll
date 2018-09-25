package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信提现请求对象")
public class HandleWithdrawReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "提现金额", required=true)
	private int withdrawAmt;

	public int getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(int withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}
}
