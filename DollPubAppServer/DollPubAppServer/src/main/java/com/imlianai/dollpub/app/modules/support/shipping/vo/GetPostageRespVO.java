package com.imlianai.dollpub.app.modules.support.shipping.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取邮费返回对象")
public class GetPostageRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户游戏币")
	private int coin;
	
	@ApiModelProperty(value = "邮费")
	private int postage;
	
	@ApiModelProperty(value = "邮费rmb")
	private int postageRMB;

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getPostage() {
		return postage;
	}

	public void setPostage(int postage) {
		this.postage = postage;
	}

	public int getPostageRMB() {
		return postageRMB;
	}

	public void setPostageRMB(int postageRMB) {
		this.postageRMB = postageRMB;
	}
}
