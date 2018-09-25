package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.domain.user.UserCommon;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("查看用户信息返回对象")
public class UserInfoRespVO extends BaseRespVO {

	@ApiModelProperty("用户信息")
	private UserCommon user;
	@ApiModelProperty("游戏币余额")
	private int coin;
	@ApiModelProperty("邀请消息数")
	private int inviteMsgNum;
	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getInviteMsgNum() {
		return inviteMsgNum;
	}

	public void setInviteMsgNum(int inviteMsgNum) {
		this.inviteMsgNum = inviteMsgNum;
	}

}
