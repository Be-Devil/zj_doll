package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import com.imlianai.zjdoll.domain.doll.DollInfo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "可兑换的娃娃对象信息")
public class ExchangeDollInfo extends ExchangeBaseDollInfo{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "兑换该娃娃需要的钻石数")
	private int jewelNum;
	
	
	public ExchangeDollInfo() {
	}
	
	public ExchangeDollInfo(int id, String name, String path, int inventory, int jewelNum, String imgRoom) {
		super.setId(id);
		super.setName(name);
		super.setPath(path);
		super.setInventory(inventory);
		super.setImgRoom(imgRoom);
		this.jewelNum = jewelNum;
	}
	
	public ExchangeDollInfo(DollInfo dollInfo, int inventory) {
		super.setId(dollInfo.getDollId());
		super.setName(dollInfo.getName());
		super.setPath(dollInfo.getImgExchange());
		super.setInventory(inventory);
		super.setImgRoom(dollInfo.getImgRoom());
		//super.setVirExchangedNum(dollInfo.getExchangeNum());
		this.jewelNum = dollInfo.getJewelNum();
	}

	public int getJewelNum() {
		return jewelNum;
	}

	public void setJewelNum(int jewelNum) {
		this.jewelNum = jewelNum;
	}
}
