package com.imlianai.zjdoll.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.core.trade.util.weixin.WeixinPreOrderRes;

public class ChargeGetChargeBillRespVO extends BaseRespVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("微信预订单")
	private WeixinPreOrderRes wxRes;

	@ApiModelProperty("支付宝预订单")
	private String aliRes;
	@ApiModelProperty("H5支付地址")
	private String payUrl;

	public WeixinPreOrderRes getWxRes() {
		return wxRes;
	}

	public void setWxRes(WeixinPreOrderRes wxRes) {
		this.wxRes = wxRes;
	}

	public String getAliRes() {
		return aliRes;
	}

	public void setAliRes(String aliRes) {
		this.aliRes = aliRes;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

}
