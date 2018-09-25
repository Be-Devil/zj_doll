package com.imlianai.zjdoll.app.modules.support.invite.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.zjdoll.domain.invite.InviteRewardRecord;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.invite.util.InviteUtil;

public class InviteMineRespVO extends BaseRespVO {

	@ApiModelProperty("我的邀请码,邀请码>0即可以邀请注册，否则是引导微信注册")
	private long invitedCode = 0;

	@ApiModelProperty("邀请码右上角帮助内容描述")
	private String invitedHtml = "1、邀请奖励<br/>"
			+ "<font color='#EE1E1E'>微信登录可获得福利码，</font>每邀请一个好友微信登录成功后，双方都获"+InviteUtil.getRegReward()+"币，最高可获"+InviteUtil.getRegReward()*InviteUtil.getInviteTimeLimit()+"币/人。（双方都是微信登录才有效，您当前为%s登录）<br/><br/>"
			+ "2、助攻奖励<br/>" + "你邀请的好友抓中第1个娃娃，你可以获得"+InviteUtil.getSuccessReward()+"币奖励。";

	@ApiModelProperty("邀请二维码")
	private String qrcode = "http://www.miaolimiaoli.com/img/wawaxingqiu.png";
	@ApiModelProperty("邀请好友各得20币的描述")
	private String inviteRewardDes = "邀请好友微信登录双方各得"+InviteUtil.getRegReward()+"币";

	@ApiModelProperty("分享合成图底部奖励的描述")
	private String shareRewardDes = "下载即可 <font color='#EE1E1E'>获得"+(InviteUtil.getRewardFirstReg())+"币</font> ，输入好友邀请码 <font color='#EE1E1E'> 互得"+InviteUtil.getRegReward()+"币</font> ，分享给好友最高获"+InviteUtil.getRegReward()*InviteUtil.getInviteTimeLimit()+"币！";

	@ApiModelProperty("助攻列表空时描述")
	private String invitesuccessDes = "你邀请的好友抓中第1个娃娃，你可以获得 <font color='#EE1E1E'>"+InviteUtil.getSuccessReward()+"币</font> 奖励。";

	@ApiModelProperty("滚动栏")
	private List<String> srcollList;
	@ApiModelProperty("邀请奖励列表")
	private List<InviteRewardRecord> inviteList;
	@ApiModelProperty("助攻奖励列表")
	private List<InviteRewardRecord> successList;
	@ApiModelProperty("分享代码")
	private String shareCode="share_invite";

	public List<InviteRewardRecord> getInviteList() {
		return inviteList;
	}

	public void setInviteList(List<InviteRewardRecord> inviteList) {
		this.inviteList = inviteList;
	}

	public List<InviteRewardRecord> getSuccessList() {
		return successList;
	}

	public void setSuccessList(List<InviteRewardRecord> successList) {
		this.successList = successList;
	}

	public long getInvitedCode() {
		return invitedCode;
	}

	public void setInvitedCode(long invitedCode) {
		this.invitedCode = invitedCode;
	}

	public String getInvitedHtml() {
		return invitedHtml;
	}

	public void setInvitedHtml(String invitedHtml) {
		this.invitedHtml = invitedHtml;
	}

	public List<String> getSrcollList() {
		return srcollList;
	}

	public void setSrcollList(List<String> srcollList) {
		this.srcollList = srcollList;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getInviteRewardDes() {
		return inviteRewardDes;
	}

	public void setInviteRewardDes(String inviteRewardDes) {
		this.inviteRewardDes = inviteRewardDes;
	}

	public String getShareRewardDes() {
		return shareRewardDes;
	}

	public void setShareRewardDes(String shareRewardDes) {
		this.shareRewardDes = shareRewardDes;
	}

	public String getInvitesuccessDes() {
		return invitesuccessDes;
	}

	public void setInvitesuccessDes(String invitesuccessDes) {
		this.invitesuccessDes = invitesuccessDes;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}

}
