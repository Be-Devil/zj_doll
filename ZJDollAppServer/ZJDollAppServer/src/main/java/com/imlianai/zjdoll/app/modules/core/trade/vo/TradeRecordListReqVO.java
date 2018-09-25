package com.imlianai.zjdoll.app.modules.core.trade.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

public class TradeRecordListReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	private Long uid;
	
	@ParamCheck
	@ApiModelProperty("页码，1开始")
	private int page;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
