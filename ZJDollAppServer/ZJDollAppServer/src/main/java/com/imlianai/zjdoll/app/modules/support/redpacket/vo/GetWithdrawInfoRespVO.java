package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "提现信息返回对象")
public class GetWithdrawInfoRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "提现信息")
	private WithdrawInfo withdrawInfo;

	public WithdrawInfo getWithdrawInfo() {
		return withdrawInfo;
	}

	public void setWithdrawInfo(WithdrawInfo withdrawInfo) {
		this.withdrawInfo = withdrawInfo;
	}
}
