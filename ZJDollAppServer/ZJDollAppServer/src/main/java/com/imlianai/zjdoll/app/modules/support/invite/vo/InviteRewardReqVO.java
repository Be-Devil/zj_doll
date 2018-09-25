package com.imlianai.zjdoll.app.modules.support.invite.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@ApiModel("领取邀请奖励请求")
public class InviteRewardReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty("用户ID")
	private Long uid;

	@ApiModelProperty("奖励id")
	private long rewardId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public long getRewardId() {
		return rewardId;
	}

	public void setRewardId(long rewardId) {
		this.rewardId = rewardId;
	}
}
