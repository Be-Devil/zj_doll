package com.imlianai.dollpub.app.modules.support.xxrecharge.vo;

import java.io.Serializable;

/**
 * 心行充值返回对象
 * @author cls
 *
 */
public class TradeChargeRespVO implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * 响应码(0:成功)
	 */
	private int rspCode;
	/**
	 * 响应信息
	 */
	private String rspMsg;
	/**
	 * 平台订单号
	 */
	private long taskId;

	public int getRspCode() {
		return rspCode;
	}

	public void setRspCode(int rspCode) {
		this.rspCode = rspCode;
	}

	public String getRspMsg() {
		return rspMsg;
	}

	public void setRspMsg(String rspMsg) {
		this.rspMsg = rspMsg;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
}
