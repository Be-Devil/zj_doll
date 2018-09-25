package com.imlianai.zjdoll.app.modules.publics.msg.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class MsgGetReqVO extends BaseReqVO {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 显示数量
	 */
	private Integer badge;
	
	public MsgGetReqVO(){
	}
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Integer getBadge() {
		return badge;
	}
	public void setBadge(Integer badge) {
		this.badge = badge;
	}
	
}
