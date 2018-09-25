package com.imlianai.dollpub.app.modules.support.exchange.vo;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "可合成的娃娃对象信息")
public class ComposeDollInfo extends ExchangeBaseDollInfo{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 合成该娃娃需要的普通娃娃碎片
	 */
	private int commDebrisNum;
	/**
	 * 合成该娃娃需要的稀有娃娃碎片
	 */
	private int rareDebrisNum;
	
	public ComposeDollInfo() {
	}
	
	public ComposeDollInfo(int id, String name, String path, int inventory, int commDebrisNum, int rareDebrisNum, String imgRoom) {
		super.setId(id);
		super.setName(name);
		super.setPath(path);
		super.setInventory(inventory);
		super.setImgRoom(imgRoom);
		this.commDebrisNum = commDebrisNum;
		this.rareDebrisNum = rareDebrisNum;
	}
	
	public int getCommDebrisNum() {
		return commDebrisNum;
	}
	
	public void setCommDebrisNum(int commDebrisNum) {
		this.commDebrisNum = commDebrisNum;
	}
	
	public int getRareDebrisNum() {
		return rareDebrisNum;
	}
	
	public void setRareDebrisNum(int rareDebrisNum) {
		this.rareDebrisNum = rareDebrisNum;
	}
}
