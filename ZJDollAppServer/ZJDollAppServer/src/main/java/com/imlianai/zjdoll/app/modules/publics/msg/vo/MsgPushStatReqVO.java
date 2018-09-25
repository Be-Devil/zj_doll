package com.imlianai.zjdoll.app.modules.publics.msg.vo;

import io.swagger.annotations.ApiModelProperty;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class MsgPushStatReqVO extends BaseReqVO {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 显示数量
	 */
	@ApiModelProperty("推送id")
	private long pushId;
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public long getPushId() {
		return pushId;
	}
	public void setPushId(long pushId) {
		this.pushId = pushId;
	}
	
	
	
}
