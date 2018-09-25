package com.imlianai.dollpub.app.modules.support.pack.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取娃娃列表请求对象")
public class GetDollListReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "列表最后的ID(第一页为0)", required = true)
	private long lastId;
	
	@ApiModelProperty(value = "状态(-1:全部,0:寄存中,8:已发货,3:已兑换)", required = true)
	private int status = -1;

	public long getLastId() {
		return lastId;
	}

	public void setLastId(long lastId) {
		this.lastId = lastId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
