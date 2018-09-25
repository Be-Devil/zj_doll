package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.constants.RedpacketConstants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "邀请信息返回对象")
public class GetInviteInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "邀请图片")
	private String inviteImg = RedpacketConstants.INVITE_IMG;
	
	@ApiModelProperty(value = "邀请码")
	private String shareCode = "share_invite_friend";
	
	public String getInviteImg() {
		return inviteImg;
	}

	public void setInviteImg(String inviteImg) {
		this.inviteImg = inviteImg;
	}

	public String getShareCode() {
		return shareCode;
	}

	public void setShareCode(String shareCode) {
		this.shareCode = shareCode;
	}
}
