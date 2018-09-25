package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import java.io.Serializable;

import com.imlianai.zjdoll.app.modules.support.invite.util.InviteUtil;

import io.swagger.annotations.ApiModelProperty;

public class InviteInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("用户头像")
	private String uhead;
	
	@ApiModelProperty("用户名称")
	private String uname;
	
	@ApiModelProperty("我的邀请码,邀请码>0即可以邀请注册，否则是引导微信注册")
	private long invitedCode = 0;
	
	@ApiModelProperty("邀请二维码")
	private String qrcode = "http://lianai-image-sys.qiniudn.com/mqww/qrcode/wwqr.png";
	
	@ApiModelProperty("分享合成图底部奖励的描述")
	private String shareRewardDes = "下载即可 <font color='#EE1E1E'>获得"+(InviteUtil.getRewardFirstReg())+"币</font> ，输入邀请码 <font color='#EE1E1E'>再获"+InviteUtil.getRegReward()+"币</font> ，分享给好友最高获"+InviteUtil.getRegReward()*InviteUtil.getInviteTimeLimit()+"币！";

	@ApiModelProperty("分享代码")
	private String shareCode="share_invite";
	
	public String getUhead() {
		return uhead;
	}

	public void setUhead(String uhead) {
		this.uhead = uhead;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public long getInvitedCode() {
		return invitedCode;
	}

	public void setInvitedCode(long invitedCode) {
		this.invitedCode = invitedCode;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getShareRewardDes() {
		return shareRewardDes;
	}

	public void setShareRewardDes(String shareRewardDes) {
		this.shareRewardDes = shareRewardDes;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
}
