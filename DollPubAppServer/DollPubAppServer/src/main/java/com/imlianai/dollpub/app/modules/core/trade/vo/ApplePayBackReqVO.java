package com.imlianai.dollpub.app.modules.core.trade.vo;

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
	private Long uid;

	/**
	 * 凭证
	 */
	@ParamCheck
	private String transactionReceipt;
	
	@ApiModelProperty( "客户id")
	private Integer customerId;

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "ApplePayBackReqVO [uid=" + uid + ", transactionReceipt="
				+ transactionReceipt + "]";
	}

}
