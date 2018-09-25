package com.imlianai.zjdoll.app.modules.support.signin.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取签到奖品列表对象")
public class GetBoxContentRespVO extends BaseRespVO {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "奖品列表")
	private List<SigninAwardInfo> awards;
	
	@ApiModelProperty(value = "玩法说明")
	private String playingDesc;
	
	@ApiModelProperty(value = "累计签到次数")
	private int times;
	
	@ApiModelProperty("签到状态(0:今日未签到，1:今日已签到，2:审核中)")
	private int signinStatus;
	
	@ApiModelProperty("奖品编号")
	private long id;

	public List<SigninAwardInfo> getAwards() {
		return awards;
	}

	public void setAwards(List<SigninAwardInfo> awards) {
		this.awards = awards;
	}

	public String getPlayingDesc() {
		return playingDesc;
	}

	public void setPlayingDesc(String playingDesc) {
		this.playingDesc = playingDesc;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getSigninStatus() {
		return signinStatus;
	}

	public void setSigninStatus(int signinStatus) {
		this.signinStatus = signinStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
