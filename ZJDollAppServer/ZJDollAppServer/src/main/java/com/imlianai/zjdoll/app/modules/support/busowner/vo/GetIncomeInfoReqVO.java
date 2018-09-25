package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("经营收益请求对象")
public class GetIncomeInfoReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "设备ID")
	private int busId;

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}
}