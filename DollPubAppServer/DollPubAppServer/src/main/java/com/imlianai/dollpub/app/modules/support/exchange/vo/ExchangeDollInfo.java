package com.imlianai.dollpub.app.modules.support.exchange.vo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "可兑换的娃娃对象信息")
public class ExchangeDollInfo extends ExchangeBaseDollInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 兑换该娃娃需要的钻石数
	 */
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

	public int getJewelNum() {
		return jewelNum;
	}

	public void setJewelNum(int jewelNum) {
		this.jewelNum = jewelNum;
	}
}
