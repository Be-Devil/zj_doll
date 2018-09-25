package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "微信授权请求对象")
public class BoundWechatReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "openId", required=true)
	private String openId;
	
	@ApiModelProperty(value = "unionId", required=true)
	private String unionId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}
