package com.imlianai.dollpub.app.modules.core.api.vo;

import com.imlianai.dollpub.app.controller.vo.BaseSignReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value="娃娃机列表请求对象")
public class DollBusListReqVO extends BaseSignReqVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="页码，默认是0",hidden=true)
	private int page = 0;
	
	@ApiModelProperty(value="每页设备数量，默认是20",hidden=true)
	private int size = 2000;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
