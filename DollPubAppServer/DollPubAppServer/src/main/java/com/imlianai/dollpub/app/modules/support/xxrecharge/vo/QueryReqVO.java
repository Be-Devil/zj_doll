package com.imlianai.dollpub.app.modules.support.xxrecharge.vo;

import java.io.Serializable;

import com.imlianai.dollpub.app.modules.support.xxrecharge.constants.XxingRechargeConstants;

/**
 * 心行充值查询请求对象
 * @author cls
 *
 */
public class QueryReqVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 服务接口编号
	 */
	private int clientId = XxingRechargeConstants.clientId;
	/**
	 * 商户号
	 */
	private int merchant = XxingRechargeConstants.merchant;
	/**
	 * 商户本地订单号
	 */
	private String outTradeNo;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 时间戳
	 */
	private long ts;
	/**
	 * 接口版本号
	 */
	private String version = XxingRechargeConstants.VERSION;

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getMerchant() {
		return merchant;
	}

	public void setMerchant(int merchant) {
		this.merchant = merchant;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
