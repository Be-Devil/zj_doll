package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("扭蛋机-用户上机请求对象")
public class PlayReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("是否尝试(0:否，1:是)")
	private int isTry;
	
	@ApiModelProperty("扭蛋机ID")
	private long machineId;
	
	@ApiModelProperty("类型(0:使用余额,1:使用时光劵)")
	private int type;
	
	@ApiModelProperty("次数")
	private int time;
	
	@ApiModelProperty("操作ID")
	private long optId;
	
	public int getIsTry() {
		return isTry;
	}

	public void setIsTry(int isTry) {
		this.isTry = isTry;
	}



	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getMachineId() {
		return machineId;
	}

	public void setMachineId(long machineId) {
		this.machineId = machineId;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}
}
