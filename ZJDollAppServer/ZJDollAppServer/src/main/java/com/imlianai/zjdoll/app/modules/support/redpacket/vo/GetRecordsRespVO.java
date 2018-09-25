package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModelProperty;

public class GetRecordsRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "红包明细")
	private List<RedpacketLogRes> records;

	public List<RedpacketLogRes> getRecords() {
		return records;
	}

	public void setRecords(List<RedpacketLogRes> records) {
		this.records = records;
	}
}
