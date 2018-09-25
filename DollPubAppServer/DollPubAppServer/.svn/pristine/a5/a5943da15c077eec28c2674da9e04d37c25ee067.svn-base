package com.imlianai.dollpub.app.modules.core.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.dollpub.machine.iface.domain.MachineDevice;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@ApiModel(value = "娃娃机设置响应对象")
public class DollSettingQueryRespVO extends BaseRespVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("设备id")
	private int busId;
	@ApiModelProperty("游戏时间，单位秒")
	private int gameTime;
	@ApiModelProperty("捉力概率，出奖概率取值范围（1-250）,意义为平均捉中一次所需的累计游戏次数")
	private int probability;

	public DollSettingQueryRespVO() {
	}

	public DollSettingQueryRespVO(MachineDevice device) {
		if (device!=null) {
			this.setBusId(busId);
			this.setGameTime(device.getPlayTime());
			this.setProbability(device.getStrong());
		}		
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public int getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

}
