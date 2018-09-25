package com.imlianai.zjdoll.app.modules.support.exchange.vo;

import java.util.List;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "回收娃娃列表请求对象")
public class RecycleListReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "回收娃娃列表", required = true)
	private List<Long> dollList;

	public List<Long> getDollList() {
		return dollList;
	}

	public void setDollList(List<Long> dollList) {
		this.dollList = dollList;
	}
}
