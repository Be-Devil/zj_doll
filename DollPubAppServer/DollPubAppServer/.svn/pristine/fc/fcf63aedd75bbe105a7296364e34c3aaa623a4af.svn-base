package com.imlianai.dollpub.app.modules.support.pack.vo;

import java.util.List;

import com.imlianai.dollpub.domain.doll.BaseDollInfo;
import com.imlianai.rpc.support.common.cmd.BaseRespVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "获取娃娃列表返回对象")
public class GetDollListRespVO extends BaseRespVO{

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "数量")
	private int size;
	
	@ApiModelProperty(value = "娃娃列表")
	private List<BaseDollInfo> dollList;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<BaseDollInfo> getDollList() {
		return dollList;
	}

	public void setDollList(List<BaseDollInfo> dollList) {
		this.dollList = dollList;
	}
}
