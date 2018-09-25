package com.imlianai.zjdoll.app.modules.support.shipping.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@ApiModel(value = "取消发货订单请求对象")
public class CancelBillReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "用户id")
	private Long uid;
	@ApiModelProperty(value = "订单号")
	private long orderNum;

	public long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(long orderNum) {
		this.orderNum = orderNum;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
