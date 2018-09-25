package com.imlianai.zjdoll.app.modules.support.address.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改默认收货地址返回对象")
public class UpdateDefAddressReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "地址ID", required = true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
