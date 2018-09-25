package com.imlianai.dollpub.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.BaseModel;

@ApiModel("登录同步返回对象")
public class UserLoginSyncData extends BaseModel{

	@ApiModelProperty("账户余额")
	private int coin;
	@ApiModelProperty("注册奖励")
	private int regReward;
	@ApiModelProperty("邀请奖励")
	private int inviteReward;
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public int getRegReward() {
		return regReward;
	}
	public void setRegReward(int regReward) {
		this.regReward = regReward;
	}
	public int getInviteReward() {
		return inviteReward;
	}
	public void setInviteReward(int inviteReward) {
		this.inviteReward = inviteReward;
	}
	
}
