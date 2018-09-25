package com.imlianai.zjdoll.app.modules.core.trade.catalog.domain;

import java.util.Date;

import com.imlianai.rpc.support.common.BaseModel;

/**
 * 周月卡充值登录领取奖励记录
 * @author cls
 *
 */
public class ChargeCardReceiveAwardLog extends BaseModel{

	private static final long serialVersionUID = 1L;

	private Long uid;
	/**
	 * 领取日期
	 */
	private int dateCode;
	/**
	 * 类型(2:周卡，3:月卡)
	 */
	private int type;
	
	private Date createTime;

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public int getDateCode() {
		return dateCode;
	}

	public void setDateCode(int dateCode) {
		this.dateCode = dateCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
