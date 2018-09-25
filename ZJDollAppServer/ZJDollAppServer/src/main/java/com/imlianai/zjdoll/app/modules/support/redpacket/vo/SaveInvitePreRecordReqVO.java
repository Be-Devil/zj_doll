package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "保存预邀请记录请求对象")
public class SaveInvitePreRecordReqVO extends BaseModel{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("uid")
	private Long uid;
	
	@ApiModelProperty("unionId")
	private String unionId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
}
