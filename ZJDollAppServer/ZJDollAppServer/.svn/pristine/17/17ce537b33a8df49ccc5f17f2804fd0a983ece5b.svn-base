package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.zjdoll.domain.redpacket.RedpacketRes;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "邀请好友情况返回对象")
public class GetInviteSituationRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "邀请人数是否已满3人(true:是)")
	private boolean isOverNum;
	
	@ApiModelProperty(value = "是否在72小时内(true:是)")
	private boolean isBefore;
	
	@ApiModelProperty(value = "邀请红包信息")
	private RedpacketRes inviteRedpacketRes;

	public boolean getIsOverNum() {
		return isOverNum;
	}

	public void setIsOverNum(boolean isOverNum) {
		this.isOverNum = isOverNum;
	}

	public boolean getIsBefore() {
		return isBefore;
	}

	public void setIsBefore(boolean isBefore) {
		this.isBefore = isBefore;
	}

	public RedpacketRes getInviteRedpacketRes() {
		return inviteRedpacketRes;
	}

	public void setInviteRedpacketRes(RedpacketRes inviteRedpacketRes) {
		this.inviteRedpacketRes = inviteRedpacketRes;
	}
}
