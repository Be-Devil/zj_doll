package com.imlianai.zjdoll.app.modules.support.dailytask.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "宝箱中的奖品对象")
public class AwardInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public AwardInfo() {
	}
	
	public AwardInfo(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	@ApiModelProperty("奖品类型(0:游戏币，1:红包)")
	private int type;
	
	@ApiModelProperty("奖品文案")
	private String desc;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
