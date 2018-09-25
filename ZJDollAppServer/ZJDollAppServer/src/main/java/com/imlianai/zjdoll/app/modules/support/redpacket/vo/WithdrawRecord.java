package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "提现记录")
public class WithdrawRecord implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "说明")
	private String remark;
	
	@ApiModelProperty(value = "提现金额(负数)")
	private int withdrawAmt;
	
	@ApiModelProperty(value = "提现时间")
	private long withdrawTime;
	
	public WithdrawRecord(){
		
	}

	public WithdrawRecord(String remark, int withdrawAmt, long withdrawTime) {
		this.remark = remark;
		this.withdrawAmt = withdrawAmt;
		this.withdrawTime = withdrawTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getWithdrawAmt() {
		return withdrawAmt;
	}

	public void setWithdrawAmt(int withdrawAmt) {
		this.withdrawAmt = withdrawAmt;
	}

	public long getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(long withdrawTime) {
		this.withdrawTime = withdrawTime;
	}
}
