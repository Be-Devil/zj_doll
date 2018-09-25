package com.imlianai.dollpub.app.modules.support.banner.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="分享内容请求")
public class BannerReqVO extends BaseReqVO{
	@ApiModelProperty(value="分享代码")
	private String shareCode;
	
	private int customerId;

	private int agintId;
	
	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getAgintId() {
		return agintId;
	}

	public void setAgintId(int agintId) {
		this.agintId = agintId;
	}

}
