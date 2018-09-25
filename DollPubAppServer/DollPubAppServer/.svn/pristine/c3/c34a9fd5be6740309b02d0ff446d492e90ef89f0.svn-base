package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.rpc.support.utils.StringUtil;

@SuppressWarnings("serial")
@ApiModel("登录返回对象")
public class UserLoginRespVO extends BaseRespVO {

	@ApiModelProperty("登录校验码")
	private String loginKey;
	@ApiModelProperty("用户基础信息")
	private UserCommon user;
	@ApiModelProperty("登录额外信息,现有字段 coin-游戏币余额")
	private UserLoginSyncData additionData ;
	
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


}
