package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.constants.RedpacketConstants;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "可开启的红包列表返回对象")
public class RedpacketListRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "红包最大数量")
	private int maxSize = 14;

	@ApiModelProperty(value = "红包列表信息")
	private List<InviteRedpacketRes> inviteRedpacks;
	
	@ApiModelProperty(value = "开启红包扣取的积分")
	private int score = RedpacketConstants.COSE_SCORE;
	
	@ApiModelProperty(value = "邀请人数是否已满3人(true:是)")
	private boolean isOverNum;
	
	public List<InviteRedpacketRes> getInviteRedpacks() {
		return inviteRedpacks;
	}

	public void setInviteRedpacks(List<InviteRedpacketRes> inviteRedpacks) {
		this.inviteRedpacks = inviteRedpacks;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean getIsOverNum() {
		return isOverNum;
	}

	public void setIsOverNum(boolean isOverNum) {
		this.isOverNum = isOverNum;
	}
}
