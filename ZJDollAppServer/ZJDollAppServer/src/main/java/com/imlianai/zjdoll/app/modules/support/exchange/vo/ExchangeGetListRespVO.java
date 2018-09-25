package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "娃娃合成列表返回对象")
public class ExchangeGetListRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户钻石数")
	private int jewel;
	
	@ApiModelProperty(value = "可兑换的娃娃列表")
	private List<ExchangeDollInfo> exchangeDolls;

	public int getJewel() {
		return jewel;
	}

	public void setJewel(int jewel) {
		this.jewel = jewel;
	}

	public List<ExchangeDollInfo> getExchangeDolls() {
		return exchangeDolls;
	}

	public void setExchangeDolls(List<ExchangeDollInfo> exchangeDolls) {
		this.exchangeDolls = exchangeDolls;
	}
}
