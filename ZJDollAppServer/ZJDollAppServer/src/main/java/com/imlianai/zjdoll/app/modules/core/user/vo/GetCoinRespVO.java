package com.imlianai.zjdoll.app.modules.core.user.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户游戏币返回对象")
public class GetCoinRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户游戏币")
	private int coin;

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}
}
