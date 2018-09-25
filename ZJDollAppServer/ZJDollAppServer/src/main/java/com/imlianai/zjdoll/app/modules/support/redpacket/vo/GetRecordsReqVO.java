package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "红包明细请求对象")
public class GetRecordsReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "页数(从1开始)")
	private int page = 1;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
