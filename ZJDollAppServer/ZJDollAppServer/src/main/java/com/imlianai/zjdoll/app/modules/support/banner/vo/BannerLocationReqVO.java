package com.imlianai.zjdoll.app.modules.support.banner.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

@SuppressWarnings("serial")
@ApiModel(value="获取banner类型请求")
public class BannerLocationReqVO extends BaseReqVO{
	@ApiModelProperty(value="banner位置")
	private int location;

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}


}
