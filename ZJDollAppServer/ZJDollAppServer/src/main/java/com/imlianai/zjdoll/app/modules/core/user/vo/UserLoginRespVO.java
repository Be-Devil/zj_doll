package com.imlianai.zjdoll.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.zjdoll.domain.msg.MsgBodyForbid;
import com.imlianai.zjdoll.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("登录返回对象")
public class UserLoginRespVO extends BaseRespVO {

	@ApiModelProperty("登录校验码")
	private String loginKey;
	@ApiModelProperty("用户基础信息")
	private UserCommon user;
	@ApiModelProperty("登录额外信息,现有字段 coin-游戏币余额")
	private UserLoginSyncData additionData ;
	@ApiModelProperty("封禁信息，当返回码是120时方会返回")
	private MsgBodyForbid forbid;
	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}

	public UserLoginSyncData getAdditionData() {
		return additionData;
	}

	public void setAdditionData(UserLoginSyncData additionData) {
		this.additionData = additionData;
	}

	public MsgBodyForbid getForbid() {
		return forbid;
	}

	public void setForbid(MsgBodyForbid forbid) {
		this.forbid = forbid;
	}


}
