package com.imlianai.zjdoll.app.modules.core.user.vo;

import com.imlianai.zjdoll.domain.busowner.BusOwnerRes;
import com.imlianai.zjdoll.domain.redpacket.RedpacketRes;
import com.imlianai.rpc.support.common.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("登录同步返回对象")
public class UserLoginSyncData extends BaseModel{

	@ApiModelProperty("账户余额")
	private int coin;
	@ApiModelProperty("注册奖励")
	private int regReward;
	@ApiModelProperty("邀请奖励")
	private int inviteReward;
	@ApiModelProperty("邀请奖励总额")
	private int inviteTotalReward;
	@ApiModelProperty("签到状态(0:今日未签到，1:今日已签到，2:审核中)")
	private int signinStatus;
	@ApiModelProperty("红包金额")
	private double redPacketAmt;
	@ApiModelProperty("签到红包对象")
	private RedpacketRes redpacketRes;
	@ApiModelProperty("是否有可领取的红包(0:无，1:有)")
	private int hasRedpacket;
	@ApiModelProperty("兑换商城小红点，大于0需要显示小红点")
	private int shopPoint=1;
	@ApiModelProperty("兑换商城地址")
	private String shopUrl;
	@ApiModelProperty("可领取活跃度的任务数量")
	private int taskNum;
	@ApiModelProperty("萌主信息")
	private BusOwnerRes busOwnerRes;
	@ApiModelProperty("萌主h5页面地址")
	private String busOwnerUrl;
	@ApiModelProperty("萌主h5页面标题")
	private String busOwnerTitle;
	
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
	public RedpacketRes getRedpacketRes() {
		return redpacketRes;
	}
	public void setRedpacketRes(RedpacketRes redpacketRes) {
		this.redpacketRes = redpacketRes;
	}
	public int getHasRedpacket() {
		return hasRedpacket;
	}
	public void setHasRedpacket(int hasRedpacket) {
		this.hasRedpacket = hasRedpacket;
	}
	public int getShopPoint() {
		return shopPoint;
	}
	public void setShopPoint(int shopPoint) {
		this.shopPoint = shopPoint;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	public int getInviteTotalReward() {
		return inviteTotalReward;
	}
	public void setInviteTotalReward(int inviteTotalReward) {
		this.inviteTotalReward = inviteTotalReward;
	}
	public int getTaskNum() {
		return taskNum;
	}
	public void setTaskNum(int taskNum) {
		this.taskNum = taskNum;
	}
	public BusOwnerRes getBusOwnerRes() {
		return busOwnerRes;
	}
	public void setBusOwnerRes(BusOwnerRes busOwnerRes) {
		this.busOwnerRes = busOwnerRes;
	}
	public String getBusOwnerUrl() {
		return busOwnerUrl;
	}
	public void setBusOwnerUrl(String busOwnerUrl) {
		this.busOwnerUrl = busOwnerUrl;
	}
	public String getBusOwnerTitle() {
		return busOwnerTitle;
	}
	public void setBusOwnerTitle(String busOwnerTitle) {
		this.busOwnerTitle = busOwnerTitle;
	}
	
	
}
