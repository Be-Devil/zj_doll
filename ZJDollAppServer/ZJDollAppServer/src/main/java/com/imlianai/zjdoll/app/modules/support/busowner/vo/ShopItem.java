package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class ShopItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "设备ID")
	private int busId;
	
	@ApiModelProperty(value = "封面地址")
	private String img;
	
	public ShopItem() {
		
	}

	public ShopItem(int busId, String img) {
		super();
		this.busId = busId;
		this.img = img;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
