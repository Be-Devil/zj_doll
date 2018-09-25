package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

public class RoomRedpacketRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户昵称")
	private String name;
	
	@ApiModelProperty(value = "设备id")
	private int busId;
	
	@ApiModelProperty(value = "红包金额")
	private double amt;
	
	@ApiModelProperty(value = "时间")
	private String time;
	
	
	public RoomRedpacketRecord() {
		
	}
	
	public RoomRedpacketRecord(String name, int busId, double amt, String time) {
		this.name = name;
		this.busId = busId;
		this.amt = amt;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public double getAmt() {
		return amt;
	}

	public void setAmt(double amt) {
		this.amt = amt;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
