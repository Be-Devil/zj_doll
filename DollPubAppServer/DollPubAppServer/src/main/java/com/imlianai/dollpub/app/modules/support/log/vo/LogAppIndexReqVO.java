package com.imlianai.dollpub.app.modules.support.log.vo;
import com.imlianai.rpc.support.common.cmd.BaseReqVO;
public class LogAppIndexReqVO extends BaseReqVO {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 0直接进入
	 * 1.选男
	 * 2.选女
	 */
	private int type;
	/**
	 * 用户ID
	 */
	private Long uid;

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
