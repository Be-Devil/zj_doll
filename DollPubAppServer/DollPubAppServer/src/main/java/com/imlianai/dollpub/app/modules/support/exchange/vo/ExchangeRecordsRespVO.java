package com.imlianai.dollpub.app.modules.support.exchange.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "兑换记录返回对象")
public class ExchangeRecordsRespVO extends BaseRespVO {

	private static final long serialVersionUID = 1L;
	
	private List<ExchangeRecord> records;

	public List<ExchangeRecord> getRecords() {
		return records;
	}

	public void setRecords(List<ExchangeRecord> records) {
		this.records = records;
	}
}
