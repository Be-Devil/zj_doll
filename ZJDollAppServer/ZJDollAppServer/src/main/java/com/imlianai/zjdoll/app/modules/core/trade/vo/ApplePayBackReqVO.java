package com.imlianai.zjdoll.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class ApplePayBackReqVO extends BaseReqVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@ParamCheck
	@ApiModelProperty("用户id")
	private Long uid;

	/**
	 * 凭证
	 */
	@ParamCheck
	@ApiModelProperty("凭证")
	private String transactionReceipt;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTransactionReceipt() {
		return transactionReceipt;
	}

	public void setTransactionReceipt(String transactionReceipt) {
		this.transactionReceipt = transactionReceipt;
	}

	@Override
	public String toString() {
		return "ApplePayBackReqVO [uid=" + uid + ", transactionReceipt="
				+ transactionReceipt + "]";
	}

}
