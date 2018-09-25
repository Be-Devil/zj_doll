package com.imlianai.dollpub.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class ChargeGetChargeBillReqVO extends BaseReqVO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 编码
	 */
	@ParamCheck
	@ApiModelProperty("商品代码")
	private Integer code;

	/**
	 * 用户ID
	 */
	@ParamCheck
	@ApiModelProperty("用户ID")
	private Long uid;

	/**
	 * 充值类型Str
	 */
	@ParamCheck
	@ApiModelProperty("充值类型 1-微信 2-支付宝")
	private int chargeType;
	
	private String openId;
	
	@ApiModelProperty("发货单号， code与orderNum只需要传其中一个")
	private Long orderNum;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getChargeType() {
		return chargeType;
	}

	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

}
