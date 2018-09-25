package com.imlianai.dollpub.app.modules.support.userdoll.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户娃娃详情请求对象")
public class UserDollInfoReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id", required = true)
	private Long uid;

	@ApiModelProperty(value = "用户娃娃id", required = true)
	private int id;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}
