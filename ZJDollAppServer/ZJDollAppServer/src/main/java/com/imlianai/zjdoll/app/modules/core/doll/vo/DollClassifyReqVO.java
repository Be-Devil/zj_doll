package com.imlianai.zjdoll.app.modules.core.doll.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import com.imlianai.rpc.support.constants.FormatFinal;
import com.imlianai.rpc.support.manager.aspect.annotations.ParamCheck;

@SuppressWarnings("serial")
@ApiModel("娃娃机信息列表请求对象")
public class DollClassifyReqVO extends BaseReqVO {

	@ApiModelProperty( "用户id")
	private Long uid;
	
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}
