package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class BusRedpacketReqVo extends BaseReqVO {

	@ApiModelProperty("用户id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Long uid;

	@ApiModelProperty("娃娃机id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Integer busId;

	@ApiModelProperty("上机操作id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private long optId;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Integer getBusId() {
		return busId;
	}

	public void setBusId(Integer busId) {
		this.busId = busId;
	}

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}
	
	
}
