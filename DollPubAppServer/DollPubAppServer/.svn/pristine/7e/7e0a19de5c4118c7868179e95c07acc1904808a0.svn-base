package com.imlianai.dollpub.app.modules.support.userdoll.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModelProperty;

public class GetEverySizeRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "寄存中数量")
	private int storeSize;
	
	@ApiModelProperty(value = "已发货数量")
	private int shippedSize;
	
	@ApiModelProperty(value = "已兑换数量")
	private int exchangeSize;
	
	public GetEverySizeRespVO() {
		
	}
	
	public GetEverySizeRespVO(int result, boolean state, String msg) {
		super(result, state, msg);
	}

	public int getStoreSize() {
		return storeSize;
	}

	public void setStoreSize(int storeSize) {
		this.storeSize = storeSize;
	}

	public int getShippedSize() {
		return shippedSize;
	}

	public void setShippedSize(int shippedSize) {
		this.shippedSize = shippedSize;
	}

	public int getExchangeSize() {
		return exchangeSize;
	}

	public void setExchangeSize(int exchangeSize) {
		this.exchangeSize = exchangeSize;
	}
}
