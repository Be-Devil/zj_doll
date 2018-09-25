package com.imlianai.zjdoll.app.modules.support.event.twistedEggMachine.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "奖品对象")
public class AwardItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "奖品价值")
	private int coin;
	
	@ApiModelProperty(value = "奖品类型(0:游戏币，1:大奖品，2:福袋)")
	private int type;
	
	@ApiModelProperty(value = "奖品名称")
	private String awardName;
	
	@ApiModelProperty(value = "奖品图标")
	private String icon;

	public AwardItem() {
		
	}
	
	public AwardItem(int type, String awardName, int coin, String icon) {
		this.type = type;
		this.awardName = awardName;
		this.coin = coin;
		this.icon = icon;
	}
	
	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAwardName() {
		return awardName;
	}

	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
