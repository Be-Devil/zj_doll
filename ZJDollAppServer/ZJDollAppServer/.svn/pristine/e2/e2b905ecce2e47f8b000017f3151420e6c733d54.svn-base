package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.io.Serializable;
import java.util.List;

import com.imlianai.zjdoll.domain.redpacket.RedpacketRes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "我的红包信息")
public class MyRedpacketInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "可提现金额")
	private double amount;
	
	@ApiModelProperty(value = "提现、红包领取记录")
	private List<String> records;

	@ApiModelProperty(value = "说明")
	private String remark;
	
	@ApiModelProperty(value = "今日可免费开启次数")
	private int times;
	
	@ApiModelProperty(value = "积分")
	private int score;
	
	@ApiModelProperty(value = "邀请红包列表")
	private List<RedpacketRes> redpacketResList;
	
	@ApiModelProperty(value = "邀请红包信息")
	private RedpacketRes inviteRedpacketRes;
	
	@ApiModelProperty(value = "是否在72小时前(true:是)")
	private boolean isBefore;
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<String> getRecords() {
		return records;
	}

	public void setRecords(List<String> records) {
		this.records = records;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public List<RedpacketRes> getRedpacketResList() {
		return redpacketResList;
	}

	public void setRedpacketResList(List<RedpacketRes> redpacketResList) {
		this.redpacketResList = redpacketResList;
	}

	public RedpacketRes getInviteRedpacketRes() {
		return inviteRedpacketRes;
	}

	public void setInviteRedpacketRes(RedpacketRes inviteRedpacketRes) {
		this.inviteRedpacketRes = inviteRedpacketRes;
	}

	public boolean getIsBefore() {
		return isBefore;
	}

	public void setIsBefore(boolean isBefore) {
		this.isBefore = isBefore;
	}
}
