package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class RatioInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "排名")
	private int ranking;
	
	@ApiModelProperty(value = "奖励比例")
	private double rewardRatio;

	public RatioInfo() {
		
	}
	
	public RatioInfo(int ranking, double rewardRatio) {
		this.ranking = ranking;
		this.rewardRatio = rewardRatio;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public double getRewardRatio() {
		return rewardRatio;
	}

	public void setRewardRatio(double rewardRatio) {
		this.rewardRatio = rewardRatio;
	}
}
