package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import com.imlianai.zjdoll.domain.doll.DollInfo;

import io.swagger.annotations.ApiModelProperty;

public class ExchangeRecord extends ExchangeBaseDollInfo {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "兑换时间")
	private long exchangeTime;
	
	public ExchangeRecord() {
		
	}

	public ExchangeRecord(DollInfo dollInfo, long exchangeTime) {
		super.setId(dollInfo.getDollId());
		super.setName(dollInfo.getName());
		if(dollInfo.getType() == 1) {
			super.setPath(dollInfo.getImgCompose());
		} else {
			super.setPath(dollInfo.getImgExchange());
		}
		this.exchangeTime = exchangeTime;
	}
	
	public long getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(long exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
}
