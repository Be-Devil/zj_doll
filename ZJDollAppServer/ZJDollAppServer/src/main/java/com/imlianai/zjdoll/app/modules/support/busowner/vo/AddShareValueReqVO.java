package com.imlianai.zjdoll.app.modules.support.busowner.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("添加分享值请求对象")
public class AddShareValueReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "unionId")
	private String unionId;
	
	@ApiModelProperty(value = "设备Id")
	private int busId;

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public int getBusId() {
		return busId;
	}

	public void setBusId(int busId) {
		this.busId = busId;
	}
}
