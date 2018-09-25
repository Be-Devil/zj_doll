package com.imlianai.zjdoll.app.modules.core.egg.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AccountInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("余额(游戏币)")
	private int amount;
	
	@ApiModelProperty("时光劵数量")
	private int num;

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
}