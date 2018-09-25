package com.imlianai.dollpub.app.modules.core.user.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class UserLoginOutReqVO extends BaseReqVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 用户id
	 */
	private Long uid;

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "UserLoginOutReqVO [uid=" + uid + "]";
	}

}
