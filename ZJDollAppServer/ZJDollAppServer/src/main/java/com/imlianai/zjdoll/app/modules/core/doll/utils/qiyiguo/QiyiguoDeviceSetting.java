package com.imlianai.zjdoll.app.modules.core.doll.utils.qiyiguo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("奇异果机器配置对象")
public class QiyiguoDeviceSetting {

	@ApiModelProperty("设备id")
	private String device_id;
	private String time;
	private int holding_mode;
	private int winning_probability;
	private double strong_force;
	private double weak_force;
	
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getHolding_mode() {
		return holding_mode;
	}

	public void setHolding_mode(int holding_mode) {
		this.holding_mode = holding_mode;
	}

	public int getWinning_probability() {
		return winning_probability;
	}

	public void setWinning_probability(int winning_probability) {
		this.winning_probability = winning_probability;
	}

	public double getStrong_force() {
		return strong_force;
	}

	public void setStrong_force(double strong_force) {
		this.strong_force = strong_force;
	}

	public double getWeak_force() {
		return weak_force;
	}

	public void setWeak_force(double weak_force) {
		this.weak_force = weak_force;
	}


}
