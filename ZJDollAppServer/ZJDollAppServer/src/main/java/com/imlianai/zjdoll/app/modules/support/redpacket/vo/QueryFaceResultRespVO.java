package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "查询是否已实名返回对象")
public class QueryFaceResultRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "是否已认证(true:已认证)")
	private boolean isAuth;
	
	@ApiModelProperty(value = "认证失败原因")
	private String reason;
	
	public QueryFaceResultRespVO() {
		
	}

	public QueryFaceResultRespVO(int result, boolean state, String msg) {
		super(result, state, msg);
	}
	public boolean isAuth() {
		return isAuth;
	}

	public void setAuth(boolean isAuth) {
		this.isAuth = isAuth;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
