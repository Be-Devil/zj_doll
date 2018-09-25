package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "提现信息")
public class WithdrawInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "是否已绑定微信")
	private boolean boundFlag;
	
	@ApiModelProperty(value = "是否已关注公众号")
	private boolean followFlag;
	
	@ApiModelProperty(value = "是否已实名认证")
	private boolean certificationFlag;
	
	@ApiModelProperty(value = "关注公众号h5地址")
	private String followUrl;
	
	@ApiModelProperty(value = "可提现总金额")
	private double amount;
	
	@ApiModelProperty(value = "提现文案")
	private String withdrawDesc;
	
	@ApiModelProperty(value = "提现记录")
	private List<WithdrawRecord> withdrawRecords;
	
	public WithdrawInfo() {
		
	}
	
	public WithdrawInfo(boolean boundFlag, boolean followFlag, boolean certificationFlag, String followUrl,
			double amount, String withdrawDesc, List<WithdrawRecord> withdrawRecords) {
		this.boundFlag = boundFlag;
		this.followFlag = followFlag;
		this.certificationFlag = certificationFlag;
		this.followUrl = followUrl;
		this.amount = amount;
		this.withdrawDesc = withdrawDesc;
		this.withdrawRecords = withdrawRecords;
	}

	public boolean isBoundFlag() {
		return boundFlag;
	}

	public void setBoundFlag(boolean boundFlag) {
		this.boundFlag = boundFlag;
	}

	public boolean isFollowFlag() {
		return followFlag;
	}

	public void setFollowFlag(boolean followFlag) {
		this.followFlag = followFlag;
	}

	public boolean isCertificationFlag() {
		return certificationFlag;
	}

	public void setCertificationFlag(boolean certificationFlag) {
		this.certificationFlag = certificationFlag;
	}

	public String getFollowUrl() {
		return followUrl;
	}

	public void setFollowUrl(String followUrl) {
		this.followUrl = followUrl;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getWithdrawDesc() {
		return withdrawDesc;
	}

	public void setWithdrawDesc(String withdrawDesc) {
		this.withdrawDesc = withdrawDesc;
	}

	public List<WithdrawRecord> getWithdrawRecords() {
		return withdrawRecords;
	}

	public void setWithdrawRecords(List<WithdrawRecord> withdrawRecords) {
		this.withdrawRecords = withdrawRecords;
	}
}
