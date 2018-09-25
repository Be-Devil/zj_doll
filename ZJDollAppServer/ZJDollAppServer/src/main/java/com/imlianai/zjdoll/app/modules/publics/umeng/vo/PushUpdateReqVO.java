package com.imlianai.zjdoll.app.modules.publics.umeng.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel("推送token更新对象")
public class PushUpdateReqVO extends BaseReqVO {

	@ApiModelProperty("用户id")
	private Long uid;

	@ApiModelProperty("推送token")
	private String pushToken;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}


}
