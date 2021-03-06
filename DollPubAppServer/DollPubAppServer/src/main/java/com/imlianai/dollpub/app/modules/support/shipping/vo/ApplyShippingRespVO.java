package com.imlianai.dollpub.app.modules.support.shipping.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "申请发货返回对象")
public class ApplyShippingRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	public ApplyShippingRespVO() {
		
	}
	
	public ApplyShippingRespVO(int result, boolean state, String msg) {
		super(result, state, msg);
	}

	@ApiModelProperty(value = "订单号")
	private long orderNum;
	
	@ApiModelProperty(value = "订单号str")
	private String orderNumStr;
	@ApiModelProperty(value = "余额")
	private long coin;
	
	@ApiModelProperty(value = "是否需要付费")
	private boolean needPay;

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public long getCoin() {
		return coin;
	}

	public void setCoin(long coin) {
		this.coin = coin;
	}

	public boolean isNeedPay() {
		return needPay;
	}

	public void setNeedPay(boolean needPay) {
		this.needPay = needPay;
	}

	public String getOrderNumStr() {
		return orderNumStr;
	}

	public void setOrderNumStr(String orderNumStr) {
		this.orderNumStr = orderNumStr;
	}
}
