package com.imlianai.zjdoll.app.modules.core.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.zjdoll.domain.user.UserCommon;
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
	@ApiModelProperty("签到状态(0:今日未签到，1:今日已签到，2:审核中)")
	private int signinStatus;
	@ApiModelProperty("红包金额")
	private double redPacketAmt;
	@ApiModelProperty("是否有可领取的红包(0:无，1:有)")
	private int hasRedpacket;
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

	public int getSigninStatus() {
		return signinStatus;
	}

	public void setSigninStatus(int signinStatus) {
		this.signinStatus = signinStatus;
	}

	public double getRedPacketAmt() {
		return redPacketAmt;
	}

	public void setRedPacketAmt(double redPacketAmt) {
		this.redPacketAmt = redPacketAmt;
	}

	public int getHasRedpacket() {
		return hasRedpacket;
	}

	public void setHasRedpacket(int hasRedpacket) {
		this.hasRedpacket = hasRedpacket;
	}
}
