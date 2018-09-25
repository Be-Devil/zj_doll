package com.imlianai.dollpub.app.controller.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(description = "请求对象")
public class BaseSignReqVO extends BaseReqVO {

	@ApiModelProperty("每次请求的秒时间戳")
	private String ts;

	@ApiModelProperty("签名")
	private String sign;

	@ApiModelProperty("商户id")
	private String appId;

	public String getTs() {
		return ts;
	}

	public void setTs(String ts) {
		this.ts = ts;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
