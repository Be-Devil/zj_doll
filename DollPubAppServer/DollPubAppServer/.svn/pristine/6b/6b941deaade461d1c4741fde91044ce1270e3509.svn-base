package com.imlianai.dollpub.app.modules.support.userdoll.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户娃娃状态请求对象")
public class UserDollStateReqVO extends BaseReqVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户id",required = true)
	private Long uid;
	
	@ApiModelProperty(value = "【娃娃状态】0:寄存中,1:成功申请发货 2:已发货,3:已兑换,4:已拒绝",required = true)
	private int status;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	
	
}
