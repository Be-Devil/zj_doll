package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;
import com.imlianai.zjdoll.app.modules.support.redpacket.vo.BusRedpacket;

public class BusAbandonRespVo extends BaseRespVO {

	@ApiModelProperty("红包对象，空即不存在可领红包")
	private BusRedpacket redpacket;

	public BusAbandonRespVo() {
	}

	public BusAbandonRespVo(BusRedpacket busRedpacket) {
		this.redpacket = busRedpacket;
	}

	public BusRedpacket getRedpacket() {
		return redpacket;
	}

	public void setRedpacket(BusRedpacket redpacket) {
		this.redpacket = redpacket;
	}
}
