package com.imlianai.zjdoll.app.modules.core.egg.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel("扭蛋机信息对象")
public class EggMachineInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("扭蛋机名称")
	private String name;
	
	@ApiModelProperty("单价")
	private int price;
	
	@ApiModelProperty("库存")
	private int inventory;

	@ApiModelProperty("详情URL列表")
	private List<String> detailUrls;
	
	@ApiModelProperty("中奖记录")
	private List<WinRecordItem> winRecords;
	
	public EggMachineInfo() {
		
	}

	public EggMachineInfo(String name, int price, int inventory, List<String> detailUrls, List<WinRecordItem> winRecords) {
		this.name = name;
		this.price = price;
		this.inventory = inventory;
		this.detailUrls = detailUrls;
		this.winRecords = winRecords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public List<String> getDetailUrls() {
		return detailUrls;
	}

	public void setDetailUrls(List<String> detailUrls) {
		this.detailUrls = detailUrls;
	}

	public List<WinRecordItem> getWinRecords() {
		return winRecords;
	}

	public void setWinRecords(List<WinRecordItem> winRecords) {
		this.winRecords = winRecords;
	}
}
