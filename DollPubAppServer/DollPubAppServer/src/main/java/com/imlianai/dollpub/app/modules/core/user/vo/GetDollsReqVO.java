package com.imlianai.dollpub.app.modules.core.user.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查看用户娃娃请求对象")
public class GetDollsReqVO extends BaseReqVO{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "被查看的用户ID", required = true)
	private Long userId;
	
	@ApiModelProperty(value = "最后一个娃娃列表ID(第一页传0)", required = true)
	private long lastId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long getLastId() {
		return lastId;
	}

	public void setLastId(long lastId) {
		this.lastId = lastId;
	}
}
