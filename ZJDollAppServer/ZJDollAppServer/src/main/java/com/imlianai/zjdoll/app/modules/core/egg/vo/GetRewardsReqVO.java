package com.imlianai.zjdoll.app.modules.core.egg.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户领取奖品请求对象")
public class GetRewardsReqVO extends BaseReqVO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("操作id")
	private long optId;

	public long getOptId() {
		return optId;
	}

	public void setOptId(long optId) {
		this.optId = optId;
	}
}
