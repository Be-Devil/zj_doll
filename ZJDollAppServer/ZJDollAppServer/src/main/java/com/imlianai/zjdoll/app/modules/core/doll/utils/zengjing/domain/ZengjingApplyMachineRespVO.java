package com.imlianai.zjdoll.app.modules.core.doll.utils.zengjing.domain;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="娃娃机上机操作响应对象")
public class ZengjingApplyMachineRespVO extends BaseRespVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="操作记录id")
	private Long optId;
	
	@ApiModelProperty(value="游戏时间,单位秒")
	private int gameTime;
	
	@ApiModelProperty(value="设备id")
	private String deviceId;

	@ApiModelProperty(value="消息")
	private String msg;
	
	@ApiModelProperty(value="true:上机成功，false:上机失败")
	private boolean isSuccess;
	
	public Long getOptId() {
		return optId;
	}

	public void setOptId(Long optId) {
		this.optId = optId;
	}

	public int getGameTime() {
		return gameTime;
	}

	public void setGameTime(int gameTime) {
		this.gameTime = gameTime;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public ZengjingApplyMachineRespVO() {
		super();
	}

	public ZengjingApplyMachineRespVO(int result, boolean state, String msg) {
		super(result, state, msg);
	}
}
