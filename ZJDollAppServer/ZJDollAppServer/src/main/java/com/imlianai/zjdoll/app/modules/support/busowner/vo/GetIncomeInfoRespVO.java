package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("经营收益返回对象")
public class GetIncomeInfoRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "月份")
	private String monthStr;
	
	@ApiModelProperty(value = "天数")
	private String dayStr;
	
	@ApiModelProperty(value = "总收入")
	private int totalIncome;
	
	@ApiModelProperty(value = "奖励比例")
	private double rewardRatio;
	
	@ApiModelProperty(value = "获得的收入")
	private int getIncome;
	
	@ApiModelProperty(value = "前三名的奖励比例")
	private List<RatioInfo> ratioInfos;
	
	public GetIncomeInfoRespVO() {
		
	}

	public String getMonthStr() {
		return monthStr;
	}

	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public String getDayStr() {
		return dayStr;
	}

	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}

	public int getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(int totalIncome) {
		this.totalIncome = totalIncome;
	}

	public double getRewardRatio() {
		return rewardRatio;
	}

	public void setRewardRatio(double rewardRatio) {
		this.rewardRatio = rewardRatio;
	}

	public int getGetIncome() {
		return getIncome;
	}

	public void setGetIncome(int getIncome) {
		this.getIncome = getIncome;
	}

	public List<RatioInfo> getRatioInfos() {
		return ratioInfos;
	}

	public void setRatioInfos(List<RatioInfo> ratioInfos) {
		this.ratioInfos = ratioInfos;
	}
}
