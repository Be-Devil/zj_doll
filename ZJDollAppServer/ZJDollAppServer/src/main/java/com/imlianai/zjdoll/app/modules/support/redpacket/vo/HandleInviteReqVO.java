package com.imlianai.zjdoll.app.modules.support.redpacket.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "分享成功处理请求对象")
public class HandleInviteReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "红包的开启id")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
