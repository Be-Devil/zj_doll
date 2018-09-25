package com.imlianai.dollpub.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.dollpub.domain.trade.TradeRecordRes;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

public class TradeRecordListRespVO extends BaseRespVO {

	@ApiModelProperty("交易记录")
	private List<TradeRecordRes> list;
	@ApiModelProperty("余额")
	private int coin;

	public List<TradeRecordRes> getList() {
		return list;
	}

	public void setList(List<TradeRecordRes> list) {
		this.list = list;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

}
