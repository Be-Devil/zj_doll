package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class BusRedpacketGetReqVo extends BaseReqVO {

	@ApiModelProperty("红包id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private long redpacketId;
	@ApiModelProperty("用户id")
	@ParamCheck(allowNull = false, format = FormatFinal.NUMBER)
	private Long uid;

	public long getRedpacketId() {
		return redpacketId;
	}

	public void setRedpacketId(long redpacketId) {
		this.redpacketId = redpacketId;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
