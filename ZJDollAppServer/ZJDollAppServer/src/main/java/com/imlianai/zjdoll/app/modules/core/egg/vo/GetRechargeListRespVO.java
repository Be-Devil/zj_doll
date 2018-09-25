package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("获取充值列表返回对象")
public class GetRechargeListRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("用户余额")
	private int amount;

	@ApiModelProperty("用户时光劵剩余数量")
	private int num;
	
	private List<RechargeItem> rechargeItems;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<RechargeItem> getRechargeItems() {
		return rechargeItems;
	}

	public void setRechargeItems(List<RechargeItem> rechargeItems) {
		this.rechargeItems = rechargeItems;
	}
}
