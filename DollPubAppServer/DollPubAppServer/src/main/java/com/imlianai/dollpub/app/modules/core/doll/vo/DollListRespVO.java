package com.imlianai.dollpub.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import com.imlianai.dollpub.domain.doll.DollBus;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

@SuppressWarnings("serial")
@ApiModel("娃娃机信息列表返回对象")
public class DollListRespVO extends BaseRespVO {

	@ApiModelProperty("娃娃机信息列表")
	private List<DollBus> list;

	public List<DollBus> getList() {
		return list;
	}

	public void setList(List<DollBus> list) {
		this.list = list;
	}

}
