package com.imlianai.zjdoll.app.modules.publics.msg.vo;

import com.imlianai.rpc.support.common.cmd.BaseReqVO;

public class MsgBugReqVO extends BaseReqVO {
	
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private Long uid;
	/**
	 * 图片
	 */
	private String url;
	/**
	 * 内容
	 */
	private String text;
	/**
	 * 设备
	 */
	private String device;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
}
